package sk.tuke.fei.hasak.notifyservice.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.fei.hasak.notifyservice.exception.NotificationNotFoundException;
import sk.tuke.fei.hasak.notifyservice.model.Notification;
import sk.tuke.fei.hasak.notifyservice.repository.NotificationRepository;

import java.util.Optional;

@Slf4j
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Iterable<Notification> findAll() { return notificationRepository.findAll(); }

    public Notification findById(long id) throws NotificationNotFoundException {
        Optional<Notification> eventOptional = notificationRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new NotificationNotFoundException("Notification with id: " + id + " was not found.");
        }

        return eventOptional.get();
    }

    public Notification findByMessageId(long id) throws NotificationNotFoundException {
        Optional<Notification> notificationOptional = notificationRepository.findByMessageId(id);

        if (notificationOptional.isEmpty()) {
            throw new NotificationNotFoundException("Notification with messageId: " + id + " was not found.");
        }

        return notificationOptional.get();
    }

    public Notification save(@NonNull Notification notification) {

        Notification savedNotification = notificationRepository.save(notification);

        log.info("[Notification-service] Notification with id: {} saved", savedNotification.getContactId());

        return savedNotification;
    }

    public Notification update(@NonNull Notification notification, long id) throws NotificationNotFoundException{
        Optional<Notification> eventOptional = notificationRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new NotificationNotFoundException("Notification with id: " + id + " was not found.");
        }

        log.info("[Notification-service] Notification with id: {} updated", id);

        notification.setContactId(id);
        notificationRepository.save(notification);

        return notification;
    }

    public void deleteById(long id) {
        log.info("[Notification-service] Notification with id: {} deleted", id);
        notificationRepository.deleteById(id);
    }
}
