package com.miaosha.order.jms;

import javax.jms.Queue;

import com.miaosha.order.OrderLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * activeMq
 */
@Log4j2
@Component
public class Producer implements CommandLineRunner {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Override
    public void run(String... args) throws Exception {
    }

    public void send(OrderLog order) {
        log.info("sms into");
        this.jmsMessagingTemplate.convertAndSend(this.queue, order);
    }

}