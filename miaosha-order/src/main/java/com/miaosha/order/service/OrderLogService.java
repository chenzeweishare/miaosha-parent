package com.miaosha.order.service;

import java.util.List;

import com.miaosha.order.OrderLog;

/**
 * @author czw
 * @date 2019/2/28 21:50
 */
public interface OrderLogService {

    OrderLog getOrderLog(Long id);

    OrderLog saveOrderLog(Long productId, Long userId);

    List<OrderLog> getOrderLogs(Long userId);

}
