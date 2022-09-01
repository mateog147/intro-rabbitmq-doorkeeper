package com.sofka.doorkeeper.service;

import com.sofka.doorkeeper.dto.MessageDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FanoutService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public Mono<String> produce(MessageDTO messageDTO){
        amqpTemplate.convertAndSend("fanout-exchange","", messageDTO.getMessage());
        return Mono.just("Message sent to the RabbitMQ Fanout Exchnage Successfully");
    }
}
