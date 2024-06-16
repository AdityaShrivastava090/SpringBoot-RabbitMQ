package com.example.springboot.producer;


import com.example.springboot.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.support.RetryTemplate;
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

    @Autowired
    private RetryTemplate retryTemplate;

    int count = 0;


    public void jsonData(User user) {
        retryTemplate.execute(context -> {
            log.info("sending data to json queue.... {}", user.toString());
            if (user.getName().equalsIgnoreCase("Aditya")) {
                log.info("retry count {}", context.getRetryCount());
                throw new RuntimeException();
            }
            rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, user);
            return null;
        });

    }


}
