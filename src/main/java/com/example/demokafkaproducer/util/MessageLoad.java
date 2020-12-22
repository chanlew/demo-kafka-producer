package com.example.demokafkaproducer.util;

import com.example.demokafkaproducer.config.ProducerConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

enum MessageLoadStatus {
    NotRunning,
    Running,
    Completed,
    Stopped,
    Error
}

@Component
public class MessageLoad {
    private static final Logger logger = LogManager.getLogger(MessageLoad.class);
    private MessageLoadStatus status = MessageLoadStatus.NotRunning;

    @JsonIgnore
    private ProducerConfig config;

    @JsonIgnore
    private CompletableFuture<Void> sendTaskFuture;

    public MessageLoad() {
    }

    public MessageLoad(ProducerConfig config) {
        this.config = config;
    }

    public MessageLoadStatus getStatus() {
        return status;
    }

    public ProducerConfig getConfig() {
        return config;
    }

    public boolean start() {
        if (status == MessageLoadStatus.Running) {
            return false;
        }

        status = MessageLoadStatus.Running;
        sendTaskFuture = CompletableFuture
                .runAsync(() -> {
                    try {
                        send();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    logger.info("Message batch task completed");
                    status = MessageLoadStatus.Completed;
                })
                .exceptionally(e -> {
                    logger.error("Message batch task failed: {}", e.getMessage());
                    status = MessageLoadStatus.Error;
                    return null;
                });
        return true;
    }

    public boolean stop() {
        if (status == MessageLoadStatus.Running) {
            sendTaskFuture.cancel(true);
        }

        status = MessageLoadStatus.Stopped;
        sendTaskFuture = null;
        return true;
    }

    private void send() throws Exception {
        try (JsonMessageSender sender = new JsonMessageSender(config.getTopic(), config.getBootstrapServers())) {
            logger.info("Sending message batch");
            MessageSource generator = new MessageSource();
            generator.getMessages().stream().forEachOrdered(m -> sender.send(m));
        }
    }
}
