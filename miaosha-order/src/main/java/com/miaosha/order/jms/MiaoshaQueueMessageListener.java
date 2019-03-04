package com.miaosha.order.jms;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.alibaba.fastjson.JSONObject;
import com.miaosha.common.constant.CommonConstant;
import com.miaosha.common.redis.RedisKeyPrefix;
import com.miaosha.common.redis.RedisUtil;
import com.miaosha.order.OrderLog;
import com.miaosha.order.controller.OrderController;
import com.miaosha.order.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MiaoshaQueueMessageListener implements MessageListener {

    @Autowired
    private JmsTemplate jmsTemplate;


    @Autowired
    private OrderLogService orderService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 采用ThreadPoolExecutor方法更加专业;
     */
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CommonConstant.System.processors, 3 * CommonConstant.System.processors, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));

    public void onMessage(Message message) {
        TextMessage msg = (TextMessage) message;
        String ms;
        try {
            ms = msg.getText();
            System.out.println("收到消息：" + ms);
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException("接收mq消息异常");
        }
        //转换成相应的对象
        final OrderLog order = JSONObject.parseObject(ms, OrderLog.class);
        if (order == null) {
            throw new RuntimeException("接收的mq消息有误");
        }
        final Long productId = order.getProductId();
        //创建秒杀订单
        threadPool.execute(new Runnable() {
            public void run() {
                try {
                    OrderLog realOrder = orderService.createOrderLog(order.getProductId(), order.getUserId());
                    //设置用户是否已经秒杀成功过某个商品的标记
                    //RedisUtil.set(CommonMethod.getMiaoshaOrderRedisKey(String.valueOf(order.getUserId()), String.valueOf(productId)), realOrder);
                    //删除排队标记
                    //RedisUtil.del(CommonMethod.getMiaoshaOrderWaitFlagRedisKey(String.valueOf(order.getUserId()), String.valueOf(productId)));
                } catch (Exception e) {
                    e.printStackTrace();
                    //还原缓存里的库存并清除内存里的商品售完标记
                    redisUtil.incr(RedisKeyPrefix.PRODUCT_STOCK + "_" + productId);
                    OrderController.productSoldOutMap.remove(productId);
                } finally {
                    //删除排队标记
                    //RedisUtil.del(CommonMethod.getMiaoshaOrderWaitFlagRedisKey(String.valueOf(order.getUserId()), String.valueOf(productId)));
                }
            }
        });
    }
}