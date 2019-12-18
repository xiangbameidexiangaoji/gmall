package com.sxt.mall.pms;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = "com.sxt.mall.pms.mapper")
public class MallPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallPmsApplication.class, args);
    }

}
