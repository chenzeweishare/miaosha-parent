package com.miaosha.order.controller;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.miaosha.common.exception.MiaoShaException;
import com.miaosha.common.redis.CommonMethod;
import com.miaosha.common.redis.RedisKeyPrefix;
import com.miaosha.common.redis.RedisUtil;
import com.miaosha.common.result.Message;
import com.miaosha.common.result.Result;
import com.miaosha.order.OrderLog;
import com.miaosha.order.jms.MQProducer;
import com.miaosha.order.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private MQProducer mqProducer;

    /**
     * jvm速度快于redis
     */
    public static ConcurrentHashMap<Long, Boolean> productSoldOutMap = new ConcurrentHashMap<>();


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
     * 普通下单
     */
    @GetMapping("/order/save")
    public Message saveOrderLog(@RequestParam Long productId) {
        return Result.getMessage(orderLogService.saveOrderLog(productId, 1000L));
    }


    /**
     * 秒杀逻辑
     * https://www.processon.com/diagraming/5a0bdf17e4b0143a78b2c5ce
     * 1. jvm内存判断
     * 2. redis内存判断
     * 3. 下单
     * 4. 减库存
     * 5. 下单或者减库存失败前面都需要合理设置
     * @param productId
     * @return
     */
    @GetMapping("/order/miaosha/save")
    public Message createOrderLog(@RequestParam Long productId) {
        if (productSoldOutMap.get(productId)) {
            Result.getErrorMessage("商品已抢完");
        }
        Long stock = RedisUtil.decr(RedisKeyPrefix.PRODUCT_STOCK + "_" + productId);
        if (stock == null) {
            Result.getErrorMessage("商品数据还未准备好");
        }
        if (stock < 0) {
            RedisUtil.incr(RedisKeyPrefix.PRODUCT_STOCK + "_" + productId);
            productSoldOutMap.put(productId, true);
            Result.getErrorMessage("商品已抢完");
        }
        OrderLog orderLog = null;
        try {
            orderLog = orderLogService.createOrderLog(productId, 1000L);
        } catch (Exception e) {
            //TODO 这里存在一定问题, 如果是失败, 这边就incr
            RedisUtil.incr(RedisKeyPrefix.PRODUCT_STOCK + "_" + productId);
            productSoldOutMap.put(productId, false);
            e.printStackTrace();
        }
        return Result.getMessage(orderLog);
    }


    /**
     * 如上述的方法, 只不过新增队列
     * @param productId
     * @return
     */
    @GetMapping("/order/miaosha/v2/save")
    public Message createOrderLogV2(@RequestParam Long productId) {
        if (productSoldOutMap.get(productId)) {
            Result.getErrorMessage("商品已抢完");

            throw new MiaoShaException("商品已抢完");
        }
        //设置排队标记，超时时间根据业务情况决定，类似分布式锁
        if (RedisUtil.set(CommonMethod.getMiaoshaOrderWaitFlagRedisKey(1000 + "", String.valueOf(productId)), "", "NX", "EX", 60)) {
            Result.getErrorMessage("排队中，请耐心等待");
        }

        Long stock = RedisUtil.decr(RedisKeyPrefix.PRODUCT_STOCK + "_" + productId);
        if (stock == null) {
            Result.getErrorMessage("商品数据还未准备好");
        }
        if (stock < 0) {
            RedisUtil.incr(RedisKeyPrefix.PRODUCT_STOCK + "_" + productId);
            productSoldOutMap.put(productId, true);
            Result.getErrorMessage("商品已抢完");
        }
        OrderLog orderLog = OrderLog.builder()
                .userId(1000L)
                .productId(productId)
                .build();
        mqProducer.sendMessage(orderLog);
        return Result.getMessage(orderLog);
    }


    @GetMapping("/order/list")
    public List<OrderLog> getOrderLogs(@RequestParam Long userId) {
        return orderLogService.getOrderLogs(userId);
    }

}
