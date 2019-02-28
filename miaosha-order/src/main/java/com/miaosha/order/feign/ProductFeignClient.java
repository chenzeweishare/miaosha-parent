package com.miaosha.order.feign;

import com.miaosha.product.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author czw
 * @date 2019/2/28 22:49
 */
@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @RequestMapping(value = "/product/info", method = RequestMethod.GET)
    Product getProduct(@RequestParam("id") Long id);

    @RequestMapping(value = "/product/stock/decrease", method = RequestMethod.GET)
    int modifyDownProductStock(@RequestParam("id") Long id);
}
