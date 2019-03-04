package com.miaosha.product.rest;

import java.util.List;

import javax.annotation.PostConstruct;

import com.miaosha.common.redis.RedisKeyPrefix;
import com.miaosha.common.redis.RedisUtil;
import com.miaosha.product.Product;
import com.miaosha.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisUtil redisUtil;

    @PostConstruct
    private void init(){
        List<Product> miaoshaProducts = productService.getProducts();
        for (Product product : miaoshaProducts) {
            redisUtil.set(RedisKeyPrefix.PRODUCT_STOCK + "_" + product.getId(), String.valueOf(product.getStock()));
        }
    }

    /**
     * 查看商品详情
     * @param id
     * @return
     */
    @GetMapping("/product/info")
    public Product getProduct(@RequestParam Long id) {
        return productService.getProduct(id);
    }


    /**
     * 减去库存
     * @param id
     * @return
     */
    @GetMapping("/product/stock/decrease")
    public int modifyDownProductStock(@RequestParam Long id) {
        return productService.modifyDownProductStock(id);
    }

    /**
     * 加库存
     * @param id
     * @return
     */
    @GetMapping("/product/stock/add")
    public int modifyUpProductStock(@RequestParam Long id) {
        return productService.modifyUpProductStock(id);
    }
}
