package sk.tuke.fei.hasak.notifyservice.kafka;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sk.tuke.fei.hasak.notifyservice.model.Notification;
import sk.tuke.fei.hasak.notifyservice.service.NotificationService;

@Slf4j
@Component
public class MessageDeletedKafkaListener {

    private final NotificationService notificationService;

    @Autowired
    public MessageDeletedKafkaListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @SneakyThrows
    @KafkaListener(topics = "${kafka.topic.message.deleted}",
            groupId = "${kafka.groupId.message.deleted}", containerFactory = "listenerContainerFactorySaved")
    public void processMessageDeleted(@NonNull MessageDeleted message) {
        log.info("[Enter-events-service] received {}", message.toString());
        Notification notification = notificationService.findByMessageId(message.getMessageId());

        notificationService.deleteById(notification.getContactId());
    }

}
