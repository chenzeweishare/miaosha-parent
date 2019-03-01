package com.miaosha.order.service.impl;


import java.util.List;

import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.miaosha.common.exception.MiaoShaException;
import com.miaosha.order.OrderLog;
import com.miaosha.order.dao.OrderLogDao;
import com.miaosha.order.feign.ProductFeignClient;
import com.miaosha.order.service.OrderLogService;
import com.miaosha.product.Product;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author czw
 * @date 2019/02/28 21:32:45
 */
@Service("orderLogService")
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    private OrderLogDao orderLogDao;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public OrderLog getOrderLog(Long id) {
        return orderLogDao.findOne(id);
    }


    /**
     * 基本逻辑
     * 1.查看用户和商品是否存在(用户暂时不查询)
     * 2.查看是否有库存
     * 3.生成订单
     * 4.减少库存
     * 5.如果失败则进行回滚
     *
     * v1.0 商品表减去库存, 生成订单, 分布式锁Redisson 2.28
     * v1.0.1 加入分布式事务fescar 3.1
     * @param productId
     * @param userId
     * @return
     */
    //@Transactional
    @GlobalTransactional
    @Override
    public OrderLog saveOrderLog(Long productId, Long userId) {
        String lockKey = "orderKey";
        RLock lock = redissonClient.getLock("lockKey");
        lock.lock();
        try {
            Product product = productFeignClient.getProduct(productId);
            if (null == product) {
                throw new MiaoShaException("无相关的商品");
            }
            if (product.getStock() <= 0) {
                throw new MiaoShaException("该商品已无库存");
            }
            OrderLog orderLog = OrderLog.builder()
                    .productId(productId)
                    .userId(1000L)
                    .amount(product.getOriginalPrice())
                    .name(product.getName())
                    .build();
            orderLogDao.save(orderLog);
            if (orderLog.getId() == null || orderLog.getId() < 0) {
                throw new MiaoShaException("创建订单失败, 请稍后再试");
            }
            int count = productFeignClient.modifyDownProductStock(productId);
            if (count != 1) {
                throw new MiaoShaException("创建订单成功, 扣减库存失败");
            }
            return orderLog;
        } finally {
            lock.unlock();
        }
    }


    @Override
    public List<OrderLog> getOrderLogs(Long userId) {
        return orderLogDao.findByUserId(userId);
    }
}

