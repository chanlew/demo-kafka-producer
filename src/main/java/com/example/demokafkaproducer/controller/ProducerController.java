package com.example.demokafkaproducer.controller;

import com.example.demokafkaproducer.config.ProducerConfigBase;
import com.example.demokafkaproducer.model.ProducerConfigRequest;
import com.example.demokafkaproducer.service.ProducerService;
import com.example.demokafkaproducer.util.MessageLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/load/start")
    public ResponseEntity<MessageLoad> startLoad() {
        MessageLoad load = service.getMessageLoad();
        if (load.start()) {
            return ResponseEntity.ok(load);
        } else {
            return new ResponseEntity<>(load, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/load/stop")
    public ResponseEntity<MessageLoad> stopLoad() {
        MessageLoad load = service.getMessageLoad();
        if (load.stop()) {
            return ResponseEntity.ok(load);
        } else {
            return new ResponseEntity<>(load, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/load/status")
    public ResponseEntity<MessageLoad> getLoadStatus() {
        return ResponseEntity.ok(service.getMessageLoad());
    }
}
