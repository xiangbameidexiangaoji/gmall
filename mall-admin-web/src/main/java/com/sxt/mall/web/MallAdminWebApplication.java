package com.sxt.mall.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MallAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAdminWebApplication.class, args);
    }

}
