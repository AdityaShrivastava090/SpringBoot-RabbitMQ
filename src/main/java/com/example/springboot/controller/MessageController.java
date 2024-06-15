package com.example.springboot.controller;


import com.example.springboot.producer.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {


    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @GetMapping
    public ResponseEntity<String> sendMessage(@RequestParam String message){
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message send to rabbit mq ...");
    }



}
