package com.miaosha.order;

import javax.jms.Queue;

import lombok.extern.log4j.Log4j2;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
@RefreshScope
@Log4j2
@EnableJms
public class OrderApplication {

    @Bean
    @LoadBalanced//使用Eureka需要开启这个
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public RedissonClient getRedissonClient(){
//        Config config = new Config();
//        //指定使用单节点部署方式
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//        return Redisson.create(config);
//    }

//    /**
//     * 默认配置和端口
//     * @return
//     */
//    @Bean
//    public JedisPool getJedisPool(){
//        JedisPoolConfig poolConfig=new JedisPoolConfig();
//        poolConfig.setMaxIdle(5);
//        poolConfig.setMinIdle(1);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setNumTestsPerEvictionRun(10);
//        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
//        JedisPool pool = new JedisPool(poolConfig, "127.0.0.1",  6379,1000);
//        Jedis resource = pool.getResource();
//        log.info("resource, {}", resource);
//        log.error("initJedisPool" );
//        return pool;
//    }
//
//    @Bean
//    public RedisUtil redisUtil(){
//        log.error("initRedisUtil" );
//        return new RedisUtil();
//    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("order.queue");
    }


    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
