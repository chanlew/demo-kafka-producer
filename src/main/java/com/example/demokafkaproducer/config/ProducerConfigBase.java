package com.example.demokafkaproducer.config;

public class ProducerConfigBase {
    private String topic;

    public static ProducerConfigBase copyOf(ProducerConfig config) {
        ProducerConfigBase copy = new ProducerConfigBase();
        copy.topic = config.getTopic();
        return copy;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
