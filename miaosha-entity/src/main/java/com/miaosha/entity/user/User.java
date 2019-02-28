package com.miaosha.user;


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
@Table(name="user")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

    private static final long serialVersionUID = 3424157250276317543L;
    /**
   *用户表 
   */
  	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

   /**
   *用户 
   */
  	@Column(name = "username")
	private String username;

   /**
   *用户密码 
   */
  	@Column(name = "password")
	private String password;

   /**
   *添加时间 
   */
  	@Column(name = "create_time")
	private Timestamp createTime;


}
