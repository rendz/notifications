package io.notifications.server.infrastructure;

import io.notifications.server.application.consumer.NotificationsConsumer;
import io.notifications.server.domain.NotificationDeliveryCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class NotificationRedisDeliveryCache implements NotificationDeliveryCache {
    Logger log = LoggerFactory.getLogger(NotificationRedisDeliveryCache.class);

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public NotificationRedisDeliveryCache(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Long> deleteDeliveryStatus(String notificationIdentifier) {
        log.info("Deleting delivery status {}", notificationIdentifier);
        return redisTemplate
                .opsForSet()
                .remove(
                        getDeliveryHashmapName(notificationIdentifier),
                        notificationIdentifier);
    }

    @Override
    public Mono<Long> saveDeliveryStatus(String notificationIdentifier) {
        log.info("Saving delivery status {}", notificationIdentifier);
        return redisTemplate
                .opsForSet()
                .add(
                        getDeliveryHashmapName(notificationIdentifier),
                        notificationIdentifier);
    }

    @Override
    public Mono<Boolean> existsDeliveryStatus(String notificationIdentifier) {
        return redisTemplate.opsForSet()
                .isMember(
                        getDeliveryHashmapName(notificationIdentifier),
                        notificationIdentifier);
    }

    private String getDeliveryHashmapName(String key) {
        return "delivered_notifications_" + Math.abs(key.hashCode()) % 100;
    }
}
