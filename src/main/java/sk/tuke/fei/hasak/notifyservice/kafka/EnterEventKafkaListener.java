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
public class EnterEventKafkaListener {

    @Autowired
    public JavaMailSender mailSender;

    @KafkaListener(topics = "${mssg.from.enter.event.service}")
    public void processMessage(String message) {
        sendMessage("simon.hasak@gmail.com", "Bachelor", message);
       log.info("Message from Enter-Event-Service was received");
    }

    private void sendMessage(String to, String subject, String text) {
        System.out.println("Sending email...");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            System.out.println("Done.");
        } catch (MailException ex) {
            ex.printStackTrace();
        }
    }

}