package com.example.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue}")
    private String queue;
    @Value("${rabbitmq.json_queue}")
    private String jsonQueue;
    @Value("${rabbitmq.exchange_name}")
    private String exchange;
    @Value("${rabbitmq.routing_key}")
    private String routingKey;
    @Value("${rabbitmq.json_routing_key}")
    private String jsonRoutingKey;

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }
    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
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
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
