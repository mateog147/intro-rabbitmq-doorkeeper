package com.sofka.doorkeeper.service;

import com.sofka.doorkeeper.dto.MessageDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TopicService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public Mono<String> produce(String routingKey, MessageDTO messageDTO){
        amqpTemplate.convertAndSend("topic-exchange",routingKey, messageDTO.getMessage());
        return Mono.just("Message sent to the RabbitMQ Topic Exchnage Successfully with routing key:"+routingKey);
    }
}
