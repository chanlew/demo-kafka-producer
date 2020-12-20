package com.example.demokafkaproducer.controller;

import com.example.demokafkaproducer.config.ProducerConfigBase;
import com.example.demokafkaproducer.model.ProducerConfigRequest;
import com.example.demokafkaproducer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/kafka")
public class ProducerController {
    @Autowired
    private ProducerService service;

    @GetMapping(value = "/config")
    public ResponseEntity<ProducerConfigBase> config() {
        return ResponseEntity.ok(service.getConfig());
    }

    @PostMapping(value = "/config")
    public ResponseEntity<ProducerConfigBase> config(@RequestBody ProducerConfigRequest configRequest) {
        if (configRequest == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean success = service.updateConfig(configRequest);
        if (success)
            return ResponseEntity.ok(service.getConfig());
        else
            return ResponseEntity.badRequest().build();
    }
}
