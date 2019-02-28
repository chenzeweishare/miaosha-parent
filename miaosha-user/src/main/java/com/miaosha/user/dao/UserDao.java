package com.miaosha.user.dao;

import com.miaosha.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author czw 
 * @date 2019/02/28 21:32:45
 */
@Repository("userDao")
public interface UserDao extends JpaRepository<User, Long> {
}

