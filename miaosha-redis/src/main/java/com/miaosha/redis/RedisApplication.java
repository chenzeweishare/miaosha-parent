package com.miaosha.redis;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Log4j2
@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
@ServletComponentScan
@RefreshScope
public class RedisApplication {


    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
