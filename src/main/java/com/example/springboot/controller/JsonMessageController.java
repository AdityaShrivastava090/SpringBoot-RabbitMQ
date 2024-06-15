package com.example.springboot.controller;

import com.example.springboot.dto.User;
import com.example.springboot.producer.RabbitMQJsonProducer;
import com.example.springboot.producer.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/json")
public class JsonMessageController {


    @Autowired
    private RabbitMQJsonProducer jsonProducer;

    @GetMapping
    public ResponseEntity<String> sendMessage(@RequestBody User user){
        jsonProducer.jsonData(user);
        return ResponseEntity.ok("Message send to rabbit mq ...");
    }


}
