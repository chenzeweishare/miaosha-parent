package com.miaosha.product.rest;

import com.miaosha.common.redis.RedisUtils;
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
        redisUtils.set("name", "张三");
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
