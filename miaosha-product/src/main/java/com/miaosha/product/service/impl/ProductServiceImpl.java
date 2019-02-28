package com.miaosha.product.service.impl;


import com.miaosha.product.Product;
import com.miaosha.product.dao.ProductDao;
import com.miaosha.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author czw
 * @date 2019/02/28 21:32:45
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProduct(Long id) {
        return productDao.findOne(id);
    }

    @Override
    public int modifyDownProductStock(Long id) {
        return productDao.modifyDownProductStock(id);
    }

    @Override
    public int modifyUpProductStock(Long id) {
        return productDao.modifyUpProductStock(id);
    }
}

