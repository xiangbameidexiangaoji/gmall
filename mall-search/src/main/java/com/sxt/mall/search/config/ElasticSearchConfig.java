package com.sxt.mall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李涵林
 * @data 2021/10/31 18:19
 */
@Configuration
public class ElasticSearchConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder http = RestClient.builder(new HttpHost("192.168.130.156", 9200, "http"));
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(http);
        return restHighLevelClient;
    }
}
