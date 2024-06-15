package com.example.springboot.producer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQProducer {
    @Value("${rabbitmq.exchange_name}")
    private String exchange;

    @Value("${rabbitmq.routing_key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    public void sendMessage(String message){
        log.info("Sending message... {}", message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }





}
