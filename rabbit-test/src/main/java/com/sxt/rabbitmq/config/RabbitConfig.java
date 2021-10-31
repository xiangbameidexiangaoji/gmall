package com.sxt.rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李涵林
 * @data 2020/7/14 15:00
 */
@Configuration
public class RabbitConfig {
    /**
     * 序列化
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue(){
        return QueueBuilder.durable("order-queue")
                .build();
    }
    @Bean
    public DirectExchange exchange(){
        return (DirectExchange) ExchangeBuilder.directExchange("order-exchange")
                .durable(true)
                .build();
    }

    @Bean
    public Binding binding(DirectExchange exchange,Queue queue){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("createOrder");
    }
}
