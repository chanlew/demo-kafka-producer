package com.example.demokafkaproducer.config;

import com.example.demokafkaproducer.model.ProducerConfigRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Configuration
@ConditionalOnProperty({"kafka.topic", "kafka.bootstrapServers"})
public class ProducerConfig extends ProducerConfigBase {
    @Value("${kafka.topic}")
    public void setTopic(String topic) {
        super.setTopic(topic);
    }

    @Value("${kafka.bootstrapServers}")
    public void setBootstrapServers(String servers) {
        super.setBootstrapServers(servers);
    }

    public boolean updateWith(ProducerConfigRequest configRequest) {
        boolean success = true;
        if (configRequest.getTopic() != null) {
            System.out.println(MessageFormat.format("Updating topic from ''{0}'' to ''{1}''", getTopic(), configRequest.getTopic()));
            setTopic(configRequest.getTopic());
        }
        return success;
    }
}
