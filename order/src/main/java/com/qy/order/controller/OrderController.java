package com.qy.order.controller;

import com.qy.order.client.BookClient;
import com.qy.res.BaseRequest;
import com.qy.res.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("order")
public class OrderController {

    //    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private BookClient bookClient;

    @GetMapping("/get")
    public BaseResult<String> getOrder() {
//        //https://plus.hutool.cn/pages/bf27e7/#%E7%94%B1%E6%9D%A5
//        return restTemplate.getForObject("http://book-service/book/get", BaseResult.class);
        BaseResult<String> result = bookClient.getOrder();
        return result;
    }
}
