package com.example.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange_name}")
    private String exchange;
    @Value("${rabbitmq.queue}")
    private String queue;
    @Value("${rabbitmq.json_queue}")
    private String jsonQueue;
    @Value("${rabbitmq.routing_key}")
    private String routingKey;
    @Value("${rabbitmq.json_routing_key}")
    private String jsonRoutingKey;

    @Value("${rabbitmq.dlExchange}")
    private String dlExchange;

    @Value("${rabbitmq.dl_routing_key}")
    private String dlk;

    @Value("${rabbitmq.dl_Queue}")
    private String dlq;


//    public static final String QUEUE_DLQ_EXAMPLE = "q.error-handling-demo.dlx-dlq-example";
//    public static final String DL_QUEUE_DLQ_EXAMPLE = "q.error-handling-demo.dlx-dlq-example.dlq";
//    public static final String EXCHANGE_DLX_EXAMPLE = "x.error-handling-demo.dlx-dlq-example";
//    public static final String DL_EXCHANGE_DLX_EXAMPLE = "x.error-handling-demo.dlx-dlq-example.dlx";
//    public static final String DL_ROUTING_KEY_ORIGINAL = "dlx-before";
//    public static final String DL_ROUTING_KEY_DLQ_OVERRIDDEN = "dlx-after";


    @Bean
    public Queue queue() {
//        return new Queue(queue);
        return QueueBuilder.durable(queue)
                .withArguments(Map.of(
                        "x-dead-letter-exchange", dlExchange,
                        "x-dead-letter-routing-key", dlk
                ))
                .build();
    }
    @Bean
    public Queue jsonQueue() {
//        return new Queue(jsonQueue);
        return QueueBuilder.durable(jsonQueue)
                .withArguments(Map.of(
                        "x-dead-letter-exchange", dlExchange,
                        "x-dead-letter-routing-key", dlk
                ))
                .build();
    }
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(dlq)
                .build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(dlExchange);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(jsonRoutingKey);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(dlk);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }


//    @Bean
//    public Declarables dlxAndDlqConfig() {
//        Queue dlqDemoQueue = new Queue(QUEUE_DLQ_EXAMPLE, false, false, false,
//                Map.of(
//                        "x-dead-letter-exchange", DL_EXCHANGE_DLX_EXAMPLE,
//                        "x-dead-letter-routing-key", DL_ROUTING_KEY_DLQ_OVERRIDDEN
//                ));
//        DirectExchange dlxExchange = new DirectExchange(EXCHANGE_DLX_EXAMPLE, false, false);
//
//        Queue dlqdeadQueue = new Queue(DL_QUEUE_DLQ_EXAMPLE, false);
//        DirectExchange dlqDeadExchange = new DirectExchange(DL_EXCHANGE_DLX_EXAMPLE, false, false);
//
//        return new Declarables(
//                dlxExchange,
//                dlqDemoQueue,
//                BindingBuilder.bind(dlqDemoQueue).to(dlxExchange).with(DL_ROUTING_KEY_ORIGINAL),
//                dlqDeadExchange,
//                dlqdeadQueue,
//                BindingBuilder.bind(dlqdeadQueue).to(dlqDeadExchange).with(DL_ROUTING_KEY_DLQ_OVERRIDDEN)
//        );
//
//    }

}
