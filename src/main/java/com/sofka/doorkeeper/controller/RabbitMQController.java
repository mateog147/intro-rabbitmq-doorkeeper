package com.sofka.doorkeeper.controller;

import com.sofka.doorkeeper.dto.MessageDTO;
import com.sofka.doorkeeper.service.FanoutService;
import com.sofka.doorkeeper.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/broker")
public class RabbitMQController {

    @Autowired
    FanoutService fanoutService;

    @Autowired
    TopicService topicService;

    @PostMapping("/fanout")
    public Mono<String> sendMessageToAll(@RequestBody MessageDTO message){
            return fanoutService.produce(message);
    }

    @PostMapping("/topic")
    public Mono<String> sendMessageToTopic(@RequestParam("routingKey") String routingKey, @RequestBody MessageDTO message){
        return topicService.produce(routingKey, message);
    }

}
