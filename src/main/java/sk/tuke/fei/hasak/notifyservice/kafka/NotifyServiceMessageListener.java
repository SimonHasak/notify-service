package sk.tuke.fei.hasak.notifyservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotifyServiceMessageListener {

    @Autowired
    public JavaMailSender mailSender;

    @KafkaListener(topics = "${kafka.notify.service.event}", groupId = "${kafka.group.notify.service}")
    public void processMessage(NotifyServiceMessage message) {
        log.info("[Enter-events-service] received {}", message.toString());
        sendMessage(message.getEmail(), "Bachelor", message.getMessage());
    }

    private void sendMessage(String to, String subject, String text) {
        log.info("Sending email...");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            log.info("Done.");
        } catch (MailException ex) {
            ex.printStackTrace();
        }
    }

}
