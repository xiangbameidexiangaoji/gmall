package com.sxt.mall.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "mainThreadPoolExecutor")
    public ThreadPoolExecutor mainThreadPoolExecutor(PoolProperties poolProperties){
        LinkedBlockingDeque<Runnable> deque = new LinkedBlockingDeque<>(poolProperties.getQueueSize());

        return new ThreadPoolExecutor(
                poolProperties.getCorePoolSize(),
                poolProperties.getMaximumPoolSize(),
                10,
                TimeUnit.MINUTES,
                deque);
    }

    @Bean(name = "otherThreadPoolExecutor")
    public ThreadPoolExecutor otherThreadPoolExecutor(PoolProperties poolProperties){
        LinkedBlockingDeque<Runnable> deque = new LinkedBlockingDeque<>(poolProperties.getQueueSize());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                poolProperties.getCorePoolSize(),
                poolProperties.getMaximumPoolSize(),
                10,
                TimeUnit.MINUTES,
                deque);
        return executor;
    }
}
