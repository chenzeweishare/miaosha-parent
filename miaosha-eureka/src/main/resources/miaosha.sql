CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `username` varchar(50) NOT NULL COMMENT '用户',
  `password` varchar(200) NOT NULL COMMENT '用户密码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;



CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品数据表',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `original_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '商品原价',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
   UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `order_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单表',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) NOT NULL COMMENT '用户ID',
  `amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '订单金额',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
