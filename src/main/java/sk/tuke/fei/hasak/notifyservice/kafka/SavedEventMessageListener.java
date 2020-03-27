package sk.tuke.fei.hasak.notifyservice.kafka;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sk.tuke.fei.hasak.notifyservice.model.Notification;
import sk.tuke.fei.hasak.notifyservice.service.NotificationService;

@Slf4j
@Component
public class SavedEventMessageListener {

    private final NotificationService notificationService;

    @Autowired
    public SavedEventMessageListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${kafka.topic.saved.event.message}",
            groupId = "${kafka.groupId.saved.event.message}", containerFactory = "listenerContainerFactorySaved")
    public void processSavedEventMessage(@NonNull SavedEventMessage message) {
        log.info("[Enter-events-service] received {}", message.toString());

        notificationService.save(new Notification(message.getMessageId(), message.getEmail()));
    }

}
