package com.sxt.rabbitmq;

import com.sxt.rabbitmq.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTestApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void contextLoads() {
        User user = new User("张三", "zhangsan@sina.com");
//        //设置消息发送为 JSON
//        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //发送消息
        this.rabbitTemplate.convertAndSend("direct_exchange", "world", user);
    }

    @Test
    public void createQueue() {
        /**
         * name : 队列名字
         * durable：是否持久化
         * exclusive：是否排它
         * autoDelete：是否自动删除
         */
        Queue queue = new Queue("my-queue-01", true, false, false);
        String s = this.amqpAdmin.declareQueue(queue);
        System.out.println(s);

    }

    /**
     * 创建交换机
     */
    @Test
    public void createExchange() {
        this.amqpAdmin.declareExchange(new DirectExchange("my-direct-exchange", true, false));
    }


    @Test
    public void createBinding(){
        /**
         * destination : 目的地
         * destinationType : 目的地类型
         * exchange : 交换器
         * routingKey : 路由建
         * arguments : 参数
         */
        Binding binding = new Binding(
                "my-queue-01",
                Binding.DestinationType.QUEUE ,
                "my-direct-exchange" ,
                "hello",
                null );
        this.amqpAdmin.declareBinding(binding);
    }
}

