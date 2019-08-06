package com.miaosha.product.rest;

import java.util.List;

import com.miaosha.common.redis.RedisKeyPrefix;
import com.miaosha.common.redis.RedisUtils;
import com.miaosha.common.result.Message;
import com.miaosha.common.result.Result;
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
    private RedisUtils redisUtils;



    @GetMapping("/product/stock/refresh")
    public Message refreshStock() {
        List<Product> products = productService.getProducts();
        for (Product product : products) {
             product.setStock(100);
             productService.saveProduct(product);
             redisUtils.set(RedisKeyPrefix.PRODUCT_STOCK + "_" + product.getId(), "100");
        }
        return Result.getSuccessMessage();
    }

    /**
     * 查看商品详情
     * @param id
     * @return
     */
    @GetMapping("/product/info")
    public Product getProduct(@RequestParam Long id) {
        handleRedis();
        return productService.getProduct(id);
    }

    private void handleRedis() {
        redisUtils.set("name", "李四");
        System.out.println(redisUtils.get("name"));
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
