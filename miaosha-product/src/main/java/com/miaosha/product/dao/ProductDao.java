package com.miaosha.product.dao;

import com.miaosha.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author czw 
 * @date 2019/02/28 21:32:45
 */
@Repository("productDao")
public interface ProductDao extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("update Product set stock=(stock - 1) where id=:id and stock > 0")
    int modifyDownProductStock(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Product set stock=(stock + 1) where id=:id")
    int modifyUpProductStock(@Param("id") Long id);
}

