package com.example.springboot.producer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.support.RetryTemplate;
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

    @Autowired
    private RetryTemplate retryTemplate;


    public static final String EXCHANGE_DLX_EXAMPLE = "x.error-handling-demo.dlx-dlq-example";
    public static final String DL_ROUTING_KEY_ORIGINAL = "dlx-before";


    public void sendMessage(String message) {
        retryTemplate.execute(context -> {
            log.info("sending data to json queue.... {}", message);
            if (message.contains("Aditya")) {
                log.info("retry count {}", context.getRetryCount());
                throw new RuntimeException();
            }
            rabbitTemplate.convertAndSend(EXCHANGE_DLX_EXAMPLE, DL_ROUTING_KEY_ORIGINAL, message);
            return null;
        });
    }


}
