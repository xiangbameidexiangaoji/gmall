package com.sxt.rabbitmq.service;

import com.rabbitmq.client.Channel;
import com.sxt.rabbitmq.bean.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author 李涵林
 * @data 2020/7/14 15:10
 */
@Service
public class UserService {

    @RabbitListener(queues = "world")
    public void receiveUserMessage(Message message, Object obj, Channel channel){
        System.out.println("收到的消息:" + message.getClass());
        byte[] body = message.getBody();
        System.out.println("收到的消息是：" + obj);

        try {
            //true 重新入队     false,丢弃消息。
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RabbitListener(queues = "order-queue")
    public void receiverOrder(Message message, Order order,Channel channel){
        System.out.println("监听到新的订单生成....." + order);

        Long skuId = order.getSkuId();
        Integer num = order.getNum();
        System.out.println("库存系统正在扣除【"+skuId+"】商品的数量，此次扣除【"+num+"】件");

        if(num%2==0){
            System.out.println("库存系统扣除【"+skuId+"】库存失败");
            try {
                //回复消息失败，重新放入队列。
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                //回复消息失败，拒绝消息，并重新入队
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("库存扣除失败");
        }
        try {
            //如果执行成功，则向 rabbitmq 回复本条消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("扣除成功");
    }
}
