package com.sxt.mall.locks.controller;

import com.sxt.mall.locks.service.RedisIncrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private RedisIncrService redisIncrService;

    @GetMapping("/incr")
    public String incr(){
        this.redisIncrService.incr();
        return "ok";
    }

    @GetMapping("/incr2")
    public String incr2(){
        this.redisIncrService.incrDistribute();
        return "ok";
    }


    @GetMapping("/incr3")
    public String incr3(){
        this.redisIncrService.incrDistribute3();
        return "ok";
    }

    @GetMapping("/read")
    public String read(){
        return this.redisIncrService.read();
    }

    @GetMapping("/write")
    public String write(){
        return this.redisIncrService.write();
    }
}
