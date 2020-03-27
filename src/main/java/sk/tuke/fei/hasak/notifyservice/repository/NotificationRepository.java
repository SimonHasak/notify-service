package sk.tuke.fei.hasak.notifyservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.fei.hasak.notifyservice.model.Notification;

import java.util.Optional;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    Optional<Notification> findByMessageId(Long id);

}
