package com.miaosha.order;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
public class OrderApplication {

    @Bean
    @LoadBalanced//使用Eureka需要开启这个
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();
        //指定使用单节点部署方式
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
