package com.sofka.doorkeeper.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }

    @Bean
    Queue firstFloorQueue() {
        return new Queue("firstFloorQueue", false);
    }

    @Bean
    Queue secondFloorQueue() {
        return new Queue("secondFloorQueue", false);
    }

    @Bean
    Queue thirdFloorQueue() {
        return new Queue("thirdFloorQueue", false);
    }

    @Bean
    Binding firstFloorFanoutBinding(Queue firstFloorQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(firstFloorQueue).to(exchange);
    }

    @Bean
    Binding secondFloorFanoutBinding(Queue secondFloorQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(secondFloorQueue).to(exchange);
    }

    @Bean
    Binding thirdFloorFanoutBinding(Queue thirdFloorQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(thirdFloorQueue).to(exchange);
    }

    @Bean
    public Binding firstFloorTopicBinding(TopicExchange topic, Queue firstFloorQueue) {
        return BindingBuilder.bind(firstFloorQueue)
                .to(topic)
                .with("odd.*");
    }

    @Bean
    public Binding thirdFloorTopicBinding(TopicExchange topic, Queue thirdFloorQueue) {
        return BindingBuilder.bind(thirdFloorQueue)
                .to(topic)
                .with("odd.*");
    }

    @Bean
    public Binding secondFloorTopicBinding(TopicExchange topic, Queue secondFloorQueue) {
        return BindingBuilder.bind(secondFloorQueue)
                .to(topic)
                .with("even.*");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
