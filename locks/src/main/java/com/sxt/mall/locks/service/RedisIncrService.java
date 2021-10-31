package com.sxt.mall.locks.service;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisIncrService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    private String hello;

    public synchronized void incr() {
        this.stringRedisTemplate.opsForValue().increment("num");
    }
    public void incrDistribute3(){
        //获取锁
        RLock lock = this.redissonClient.getLock("lock");
        lock.lock();//加锁
            /**
             * 业务代码
             *  。。。。。
             */
        Object num = this.redisTemplate.opsForValue().get("num");
        Integer i = Integer.parseInt(String.valueOf(num));
        i = i+1;
//        this.redisTemplate.opsForValue().set("num", i.toString());
//        this.redisTemplate.opsForValue().increment(, )
        lock.unlock();//解锁
    }

    public String read(){
        RReadWriteLock helloValue = redissonClient.getReadWriteLock("helloValue");
        RLock rLock = helloValue.readLock();
        rLock.lock(10, TimeUnit.SECONDS);
        String a = hello;
        rLock.unlock();
        return a;
    }

    public String write(){
        RReadWriteLock helloValue = this.redissonClient.getReadWriteLock("helloValue");
        RLock wLock = helloValue.writeLock();
        wLock.lock(10, TimeUnit.SECONDS);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hello = UUID.randomUUID().toString();
        wLock.unlock();
        return hello;
    }






//    public void incrDistribute(){
//        String token = UUID.randomUUID().toString();
//        Boolean lock = this.stringRedisTemplate.opsForValue().setIfAbsent("lock", token, 3, TimeUnit.SECONDS);
//        if(lock){
//            ValueOperations<String, String> stringStringValueOperations = this.stringRedisTemplate.opsForValue();
//            String num = stringStringValueOperations.get("num");
//            if(num != null){
//                Integer i = Integer.parseInt(num);
//                i = i + 1;
//                stringStringValueOperations.set("num", i.toString());
//            }
//            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//            DefaultRedisScript<String> script1 = new DefaultRedisScript<>(script);
//            String lock1 = this.stringRedisTemplate.execute(script1, Arrays.asList("lock"), token);
//            if(!StringUtils.isEmpty(lock1)){
//                System.out.println("删除锁成功");
//            }
//        }else {
//            incrDistribute();
//        }
//
//    }

    public void incrDistribute() {
        String token = UUID.randomUUID().toString();
        Boolean lock = this.redisTemplate.opsForValue().setIfAbsent("lock", token, 3, TimeUnit.SECONDS);
        if(lock){
            this.redisTemplate.execute((RedisCallback) connection -> {
                Boolean aBoolean = connection.setNX("3".getBytes(), token.getBytes());
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                return null;
            });
        }else{

        }
        //删除锁

    }


}
