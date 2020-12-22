package com.example.demokafkaproducer.config;

public class ProducerConfigBase {
    private String topic;
    private String bootstrapServers;

    public static ProducerConfigBase copyOf(ProducerConfig config) {
        ProducerConfigBase copy = new ProducerConfigBase();
        copy.topic = config.getTopic();
        copy.bootstrapServers = config.getBootstrapServers();
        return copy;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
