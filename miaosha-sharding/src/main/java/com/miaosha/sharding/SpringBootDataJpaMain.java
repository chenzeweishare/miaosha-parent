package com.miaosha.sharding;

import com.miaosha.sharding.service.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootDataJpaMain {
    
    // CHECKSTYLE:OFF
    public static void main(final String[] args) {
    // CHECKSTYLE:ON
        ApplicationContext applicationContext = SpringApplication.run(SpringBootDataJpaMain.class, args);
        applicationContext.getBean(DemoService.class).demo();
    }
}
