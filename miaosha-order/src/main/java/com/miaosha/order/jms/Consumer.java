package com.miaosha.order.jms;

import com.miaosha.common.exception.MiaoShaException;
import com.miaosha.order.OrderLog;
import com.miaosha.order.service.OrderLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class Consumer {

    @Autowired
    private OrderLogService orderService;

    @JmsListener(destination = "order.queue")
    public void receiveQueue(OrderLog order) {
        try {
            orderService.createOrderLog(order.getProductId(), order.getUserId());
        } catch (MiaoShaException e) {
            log.info("内部异常");
        }
    }

}