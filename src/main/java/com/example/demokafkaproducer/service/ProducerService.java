package com.example.demokafkaproducer.service;

import com.example.demokafkaproducer.config.ProducerConfig;
import com.example.demokafkaproducer.config.ProducerConfigBase;
import com.example.demokafkaproducer.model.ProducerConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Autowired
    private ProducerConfig config;

    public ProducerConfigBase getConfig() {
        return ProducerConfigBase.copyOf(config);
    }

    public boolean updateConfig(ProducerConfigRequest configRequest) {
        return config.updateWith(configRequest);
    }
}
