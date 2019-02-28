package com.miaosha.order.dao;


import java.util.List;

import com.miaosha.order.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czw 
 * @date 2019/02/28 21:32:45
 */
@Repository("orderLogDao")
public interface OrderLogDao extends JpaRepository<OrderLog, Long> {

    List<OrderLog> findByUserId(Long userId);
}

