package sk.tuke.fei.hasak.notifyservice.kafka;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class NotifyServiceMessageDeserializer extends JsonDeserializer<NotifyServiceMessage> {

    public NotifyServiceMessageDeserializer() {
        super(NotifyServiceMessage.class);
    }
}
