package com.sxt.mall.ums;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.sxt.mall.ums.mapper")
public class MallUmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUmsApplication.class, args);
    }

}
