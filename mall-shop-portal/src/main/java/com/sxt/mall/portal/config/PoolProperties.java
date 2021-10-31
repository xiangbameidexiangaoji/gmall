package com.sxt.mall.portal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "mall.pool")
public class PoolProperties {
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Integer queueSize;
}
