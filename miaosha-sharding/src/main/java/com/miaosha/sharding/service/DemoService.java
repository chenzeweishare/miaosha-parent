package com.miaosha.sharding.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.miaosha.sharding.entity.Order;
import com.miaosha.sharding.entity.OrderItem;
import com.miaosha.sharding.repository.OrderItemRepository;
import com.miaosha.sharding.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    
    @Resource
    private OrderRepository orderRepository;
    
    @Resource
    private OrderItemRepository orderItemRepository;
    
    public void demo() {
        List<Long> orderIds = new ArrayList<>(10);
        List<Long> orderItemIds = new ArrayList<>(10);
        System.out.println("1.Insert--------------");
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setUserId(51);
            order.setStatus("INSERT_TEST");
            long orderId = orderRepository.save(order).getOrderId();
            orderIds.add(orderId);
            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setUserId(51);
            item.setStatus("INSERT_TEST");
            orderItemIds.add(orderItemRepository.save(item).getOrderItemId());
        }
        List<OrderItem> orderItems = orderItemRepository.findAll();
        System.out.println(orderItemRepository.findAll());
        System.out.println("2.Delete--------------");
        if (orderItems.size() > 0) {
            for (Long each : orderItemIds) {
                orderItemRepository.findOne(each);
            }
            for (Long each : orderIds) {
                orderRepository.findOne(each);
            }
        }
        System.out.println(orderItemRepository.findAll());
    }
}
