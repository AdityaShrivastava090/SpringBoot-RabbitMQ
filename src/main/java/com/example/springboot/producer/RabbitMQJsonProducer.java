package com.example.springboot.producer;


import com.example.springboot.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQJsonProducer {

    @Value("${rabbitmq.exchange_name}")
    private String exchange;

    @Value("${rabbitmq.json_routing_key}")
    private String jsonRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void jsonData(User user){
        log.info("sending data to json queue.... {}", user.toString());
        rabbitTemplate.convertAndSend(exchange,jsonRoutingKey,user);

    }


}
