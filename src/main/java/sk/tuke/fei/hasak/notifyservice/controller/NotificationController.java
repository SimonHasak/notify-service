package sk.tuke.fei.hasak.notifyservice.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import sk.tuke.fei.hasak.notifyservice.model.Notification;
import sk.tuke.fei.hasak.notifyservice.service.NotificationService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/notification")
public class NotificationController {


    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public CollectionModel<EntityModel<Notification>> findAll() {
        List<EntityModel<Notification>> notifications = StreamSupport.stream(notificationService.findAll().spliterator(), false)
                .map(notification -> new EntityModel<>(notification,
                        linkTo(methodOn(NotificationController.class).findById(notification.getContactId())).withSelfRel(),
                        linkTo(NotificationController.class).withSelfRel()))
                .collect(Collectors.toList());

        return new CollectionModel<>(notifications);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public EntityModel<Notification> findById(@PathVariable long id) {

        Notification notification = notificationService.findById(id);

        notification.add(linkTo(methodOn(NotificationController.class).findById(notification.getContactId())).withSelfRel(),
                linkTo(NotificationController.class).withSelfRel());

        return new EntityModel<>(notification);
    }

    @SneakyThrows
    @GetMapping("/message/{id}")
    public EntityModel<Notification> findByMessageId(@PathVariable long id) {

        Notification notification = notificationService.findByMessageId(id);

        notification.add(linkTo(methodOn(NotificationController.class).findByMessageId(notification.getMessageId())).withSelfRel(),
                linkTo(NotificationController.class).withSelfRel());

        return new EntityModel<>(notification);
    }

    @PostMapping()
    public EntityModel<Notification> save(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.save(notification);

        savedNotification.add(linkTo(methodOn(NotificationController.class).findById(notification.getContactId())).withSelfRel(),
                linkTo(NotificationController.class).withSelfRel());

        return new EntityModel<>(savedNotification);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public EntityModel<Notification> update(@RequestBody Notification notification, @PathVariable long id) {
        Notification updatedNotification = notificationService.update(notification, id);

        updatedNotification.add(linkTo(methodOn(NotificationController.class).findById(updatedNotification.getContactId())).withSelfRel(),
                linkTo(NotificationController.class).withSelfRel());

        return new EntityModel<>(updatedNotification);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) { notificationService.deleteById(id); }

}

