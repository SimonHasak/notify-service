package sk.tuke.fei.hasak.notifyservice.kafka;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sk.tuke.fei.hasak.notifyservice.model.Event;
import sk.tuke.fei.hasak.notifyservice.model.Notification;
import sk.tuke.fei.hasak.notifyservice.service.NotificationService;

import java.util.Objects;

@Slf4j
@Component
public class SchedulledMessageListener {

    private final JavaMailSender mailSender;

    private final NotificationService notificationService;

    @Value(value = "${enter.events.service.address}")
    private String enterEventsServiceAddress;

    @Autowired
    public SchedulledMessageListener(JavaMailSender mailSender, NotificationService notificationService) {
        this.mailSender = mailSender;
        this.notificationService = notificationService;
    }

    @SneakyThrows
    @KafkaListener(topics = "${kafka.topic.schedulled.message}",
            groupId = "${kafka.groupId.schedulled.message}", containerFactory = "listenerContainerFactorySchedulled")
    public void processSchedulledMessage(@NonNull SchedulledMessage message) {
        log.info("[Notification-service] received: {}", message.toString());
        Event event = getEventByMessageId(message.getMessageId());

        Notification notification = notificationService.findByMessageId(message.getMessageId());

        sendEmail(notification.getEmail(), "Hello from microservice...", event.getText());
    }

    private void sendEmail(String to, String subject, String text) {
        log.info("Sending email...");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            log.info("Done.");
        } catch (MailException ex) {
            log.error(ex.getMessage());
        }
    }

    private Event getEventByMessageId(long id) {
        WebClient client = WebClient.create(enterEventsServiceAddress);

        Mono<EntityModel<Event>> eventResponse = client.get()
                .uri("/enter-events/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(eventResponse.block()).getContent();
    }

}
