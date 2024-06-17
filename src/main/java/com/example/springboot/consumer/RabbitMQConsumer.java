package com.example.springboot.consumer;

import com.example.springboot.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = ("${rabbitmq.queue}"))
    public void consumer1(String message) {
        try {
            log.info("Received message from queue [{}].... {}", "${rabbitmq.queue}", message);
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    @RabbitListener(queues = ("${rabbitmq.json_queue}"))
    public void consumer2(User user) {
        try {
            log.info("Received message from queue [{}].... {}", "${rabbitmq.json_queue}", user.toString());
        } catch (Exception e) {
            log.error("error", e);
        }
    }


//    @RabbitListener(queues = QUEUE_DLQ_EXAMPLE)
//    public void dlxDlqQueueListenerOriginal(String msg) {
//        log.info("Queue [{}] received message: [{}]", QUEUE_DLQ_EXAMPLE, msg);
//        throw new AmqpRejectAndDontRequeueException("Ops, an error! Message should go to DLX and DLQ");
//    }

    @RabbitListener(queues = ("${rabbitmq.dl_Queue}"))
    public void dlxDlqQueueListenerDL(String msg) {
        log.info("Queue [{}] received message: [{}]", "${rabbitmq.dl_Queue}", msg);
    }
}
