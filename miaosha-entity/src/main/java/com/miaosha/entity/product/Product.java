package com.miaosha.product;


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
@Table(name="product")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable{

    private static final long serialVersionUID = 7075213909189721055L;
    /**
   *商品数据表 
   */
  	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

   /**
   *商品名称 
   */
  	@Column(name = "name")
	private String name;

   /**
   *商品原价 
   */
  	@Column(name = "original_price")
	private double originalPrice;

   /**
   *库存量 
   */
  	@Column(name = "stock")
	private Integer stock;

   /**
   *创建时间 
   */
  	@Column(name = "create_time")
	private Timestamp createTime;


}
