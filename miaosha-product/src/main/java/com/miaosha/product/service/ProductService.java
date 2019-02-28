package com.miaosha.product.service;

import com.miaosha.product.Product;

/**
 * @author czw
 * @date 2019/2/28 21:51
 */
public interface ProductService {

    Product getProduct(Long id);

    int modifyDownProductStock(Long id);

    int modifyUpProductStock(Long id);
}
