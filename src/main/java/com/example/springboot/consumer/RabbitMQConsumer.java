package com.example.springboot.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import static com.example.springboot.config.RabbitMQConfig.DL_QUEUE_DLQ_EXAMPLE;
import static com.example.springboot.config.RabbitMQConfig.QUEUE_DLQ_EXAMPLE;

@Service
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = ("${rabbitmq.queue}"))
    public void consume(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try{
            log.info("Received message from queue.... {} with tag {}", message, tag);
            channel.basicAck(tag,false);
        }catch (Exception e){
            log.error("error",e);
            try {
                // Negative acknowledgment with requeue
                channel.basicNack(tag, false, true); // Reject and requeue
            } catch (Exception ex) {
                log.error("error",e);
                ex.printStackTrace();
            }
        }
    }


    @RabbitListener(queues = QUEUE_DLQ_EXAMPLE)
    public void dlxDlqQueueListenerOriginal(String msg) {
        log.info("Queue [{}] received message: [{}]", QUEUE_DLQ_EXAMPLE, msg);
        throw new AmqpRejectAndDontRequeueException("Ops, an error! Message should go to DLX and DLQ");
    }

    @RabbitListener(queues = DL_QUEUE_DLQ_EXAMPLE)
    public void dlxDlqQueueListenerDL(String msg) {
        log.info("Queue [{}] received message: [{}]", DL_QUEUE_DLQ_EXAMPLE, msg);
    }
}
