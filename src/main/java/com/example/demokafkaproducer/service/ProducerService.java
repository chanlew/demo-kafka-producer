package com.example.demokafkaproducer.service;

import com.example.demokafkaproducer.config.ProducerConfig;
import com.example.demokafkaproducer.config.ProducerConfigBase;
import com.example.demokafkaproducer.model.ProducerConfigRequest;
import com.example.demokafkaproducer.util.MessageLoad;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private static final Logger logger = LogManager.getLogger(ProducerService.class);

    private ProducerConfig config;
    private MessageLoad messageLoad;

    @Autowired
    public void setConfig(ProducerConfig config) {
        this.config = config;
        messageLoad = new MessageLoad(config);
    }

    public ProducerConfigBase getConfig() {
        return ProducerConfigBase.copyOf(config);
    }

    public boolean updateConfig(ProducerConfigRequest configRequest) {
        return config.updateWith(configRequest);
    }

    public MessageLoad getMessageLoad() {
        return messageLoad;
    }
}
