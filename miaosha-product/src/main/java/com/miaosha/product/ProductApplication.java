package com.miaosha.product;

import com.miaosha.common.redis.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Log4j2
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
@ServletComponentScan
@RefreshScope
public class ProductApplication {

    @Bean
    @LoadBalanced//使用Eureka需要开启这个
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public RedisUtils redisUtils() {
        log.error("initRedisUtil" );
        return new RedisUtils();
    }


    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
