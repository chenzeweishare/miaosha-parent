package com.miaosha.product.service;

import java.util.List;

import com.miaosha.product.Product;

/**
 * @author czw
 * @date 2019/2/28 21:51
 */
public interface ProductService {

    Product getProduct(Long id);

    int modifyDownProductStock(Long id);

    int modifyUpProductStock(Long id);

    List<Product> getProducts();

    void saveProduct(Product product);
}
