package com.sxt.rabbitmq.controller;

import com.sxt.rabbitmq.bean.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 李涵林
 * @data 2020/7/14 19:33
 */
@RestController
public class OrderController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/create/order")
    public Order createOder(Long skuId,Integer num,Integer memberId){
        Order order = new Order(UUID.randomUUID().toString().replace("-", ""), skuId, num, memberId);
        this.rabbitTemplate.convertAndSend("order-exchange", "createOrder", order);
        return order;
    }

}
