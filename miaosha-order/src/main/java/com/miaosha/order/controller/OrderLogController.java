package com.miaosha.order.controller;

import java.util.List;

import com.miaosha.order.OrderLog;
import com.miaosha.order.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderLogController {

    @Autowired
    private OrderLogService orderLogService;

    /**
     * 查看订单详情
     * @param id
     * @return
     */
    @GetMapping("/order/info")
    public OrderLog getOrderLog(@RequestParam Long id) {
        return orderLogService.getOrderLog(id);
    }

    /**
     * 秒杀下单
     */
    @GetMapping("/order/save")
    public OrderLog saveOrderLog(@RequestParam Long productId) {

        return orderLogService.saveOrderLog(productId, 1000L);
    }

    @GetMapping("/order/list")
    public List<OrderLog> getOrderLogs(@RequestParam Long userId) {

        return orderLogService.getOrderLogs(userId);
    }

}
