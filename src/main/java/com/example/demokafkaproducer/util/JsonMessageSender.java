package com.example.demokafkaproducer.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class JsonMessageSender implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger(JsonMessageSender.class);

    private final String topic;
    private final KafkaProducer<String, String> producer;

    public JsonMessageSender(String topic, String bootstrapServers) {
        this.topic = topic;

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        logger.info("Creating Kafka RecordProducer");
        producer = new KafkaProducer<>(props);
    }

    public void send(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        producer.send(record);
    }

    @Override
    public void close() throws Exception {
        logger.info("Closing Kafka RecordProducer");
        producer.close();
    }
}
