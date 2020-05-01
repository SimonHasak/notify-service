package sk.tuke.fei.hasak.notifyservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import sk.tuke.fei.hasak.notifyservice.kafka.MessageDeleted;
import sk.tuke.fei.hasak.notifyservice.kafka.SchedulledMessage;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.groupId.message.deleted}")
    private String groupIdDeleted;

    @Value(value = "${kafka.groupId.schedulled.message}")
    private String groupIdSchedulled;

    @Bean
    @ConditionalOnMissingBean(name = "deletedMessageConsumerFactory")
    public ConsumerFactory<String, MessageDeleted> deletedMessageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdDeleted);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(MessageDeleted.class, false));
    }

    @Bean("listenerContainerFactorySaved")
    public ConcurrentKafkaListenerContainerFactory<String, MessageDeleted> listenerContainerFactorySaved() {
        ConcurrentKafkaListenerContainerFactory<String, MessageDeleted> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deletedMessageConsumerFactory());
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(name = "schedulledMessageConsumerFactory")
    public ConsumerFactory<String, SchedulledMessage> schedulledMessageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdSchedulled);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(SchedulledMessage.class, false));
    }

    @Bean("listenerContainerFactorySchedulled")
    public ConcurrentKafkaListenerContainerFactory<String, SchedulledMessage> listenerContainerFactorySchedulled() {
        ConcurrentKafkaListenerContainerFactory<String, SchedulledMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(schedulledMessageConsumerFactory());
        return factory;
    }
}
