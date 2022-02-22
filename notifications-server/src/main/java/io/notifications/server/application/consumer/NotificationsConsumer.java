package io.notifications.server.application.consumer;

import io.notifications.server.domain.model.Notification;
import io.notifications.server.domain.service.NotificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class NotificationsConsumer implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(NotificationsConsumer.class);

    private final ReactiveKafkaConsumerTemplate<String, Notification> kafkaConsumerTemplate;
    private final NotificationService notificationService;

    public NotificationsConsumer(
            ReactiveKafkaConsumerTemplate<String, Notification> kafkaConsumerTemplate,
            NotificationService notificationService) {
        this.kafkaConsumerTemplate = kafkaConsumerTemplate;
        this.notificationService = notificationService;
    }

    private Flux<?> consumeEmailNotification() {
        return kafkaConsumerTemplate
                .receive()
                .concatMap(consumerRecord -> {
                    log.info(
                            "received key={}, value={} from topic={}, offset={}",
                            consumerRecord.key(),
                            consumerRecord.value(),
                            consumerRecord.topic(),
                            consumerRecord.offset());

                    return notificationService
                            .deliverNotification(consumerRecord.value())
                            .map(record -> {
                                log.info("Ack");
                                consumerRecord.receiverOffset().acknowledge();
                                return record;
                            });
                })
                .onErrorContinue((err, obj) -> {
                    log.error(
                            "something bad happened while consuming : {}",
                            err.getMessage());
                });
    }

    @Override
    public void run(String... args) {
        // we have to trigger consumption
        consumeEmailNotification().subscribe();
    }
}