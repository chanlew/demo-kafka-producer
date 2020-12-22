package com.example.demokafkaproducer.util;

import com.example.demokafkaproducer.config.ProducerConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

enum MessageLoadStatus {
    New,
    Started,
    Stopped,
    Error
}

@Component
public class MessageLoad {
    private static final Logger logger = LogManager.getLogger(MessageLoad.class);
    private MessageLoadStatus status = MessageLoadStatus.New;

    @JsonIgnore
    private ProducerConfig config;

    @JsonIgnore
    private CompletableFuture<Void> sendResult;

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
        if (status == MessageLoadStatus.Started)
            return false;

        status = MessageLoadStatus.Started;
        sendResult = CompletableFuture.runAsync(this::send);
        sendResult.thenRun(() -> status = MessageLoadStatus.Stopped);
        return true;
    }

    public boolean stop() {
        status = MessageLoadStatus.Stopped;
        sendResult = null;
        return true;
    }

    private void send() {
        try (JsonMessageSender sender = new JsonMessageSender(config.getTopic(), config.getBootstrapServers())) {
            sender.send("test");
        } catch (Exception e) {
            status = MessageLoadStatus.Error;
            logger.error("Error sending message: " + e.getMessage());
        }
    }
}
