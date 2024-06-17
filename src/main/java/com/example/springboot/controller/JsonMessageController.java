package com.example.springboot.controller;

import com.example.springboot.dto.User;
import com.example.springboot.producer.RabbitMQJsonProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/json")
@Slf4j
public class JsonMessageController {


    @Autowired
    private RabbitMQJsonProducer jsonProducer;



    @GetMapping
    public ResponseEntity<String> sendMessage(@RequestBody User user){
        jsonProducer.jsonData(user);
        return ResponseEntity.ok("Message send to rabbit mq ...");
    }


}
