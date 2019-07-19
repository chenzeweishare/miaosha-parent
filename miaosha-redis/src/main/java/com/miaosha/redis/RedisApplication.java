package com.miaosha.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class RedisApplication {


    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
