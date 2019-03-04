package com.miaosha.product;

import com.miaosha.common.redis.RedisUtil;
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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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

    /**
     * 默认配置和端口
     * @return
     */
    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(1000);
        config.setMaxWaitMillis(1000);
        JedisPool pool = new JedisPool(config, "127.0.0.1",  6379,1000);
        log.error("initJedisPool" );
        return pool;
    }

    @Bean
    public RedisUtil  redisUtil(){
        log.error("initRedisUtil" );
        return new RedisUtil();
    }


    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
