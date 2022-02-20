package io.notifications.server.application.consumer;

import io.notifications.server.domain.model.Notification;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class NotificationsConsumer implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(NotificationsConsumer.class);

    private final ReactiveKafkaConsumerTemplate<String, Notification> kafkaConsumerTemplate;

    public NotificationsConsumer(
            ReactiveKafkaConsumerTemplate<String, Notification> kafkaConsumerTemplate) {
        this.kafkaConsumerTemplate = kafkaConsumerTemplate;
    }

    private Flux<Notification> consumeEmailNotification() {
        return kafkaConsumerTemplate
                .receiveAutoAck()
                .doOnNext(consumerRecord ->
                        log.info("received key={}, value={} from topic={}, offset={}",
                                consumerRecord.key(),
                                consumerRecord.value(),
                                consumerRecord.topic(),
                                consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(EmailNotification -> log.info("successfully consumed {}={}", Notification.class.getSimpleName(), EmailNotification))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    @Override
    public void run(String... args) {
        // we have to trigger consumption
        consumeEmailNotification().subscribe();
    }
}