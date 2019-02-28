package com.miaosha.order;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * @author czw
 * @date 2019/02/28 21:32:45
 */

@Entity
@Table(name="order_log")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLog implements Serializable{

    private static final long serialVersionUID = 1922739235870566153L;
    /**
     *订单表
     */
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     *用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     *用户ID
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     *订单金额
     */
    @Column(name = "amount")
    private double amount;

    /**
     *商品名称
     */
    @Column(name = "name")
    private String name;

    /**
     *备注
     */
    @Column(name = "note")
    private String note;

    /**
     *下单时间
     */
    @Column(name = "create_time")
    private Timestamp createTime;


}
