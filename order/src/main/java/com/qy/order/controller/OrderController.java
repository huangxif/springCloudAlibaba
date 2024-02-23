package com.qy.order.controller;

import com.qy.order.client.BookClient;
import com.qy.res.BaseRequest;
import com.qy.res.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("order")
public class OrderController {

    //    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BookClient bookClient;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/get")
    public BaseResult<String> getOrder() {
//        //https://plus.hutool.cn/pages/bf27e7/#%E7%94%B1%E6%9D%A5
        return restTemplate.getForObject("http://book-service/book/get", BaseResult.class);
//        loadBalancerClient.choose()
//        BaseResult<String> result = bookClient.getOrder();
//        return null;
    }

    AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);
    public static void main(String[] args) {
        int modulo = 4;
        OrderController orderController=new OrderController();
        for (int i = 0; i < 10; i++) {
            System.out.println(orderController.test(modulo));
        }

    }

    public  int test(int modulo) {

        for (; ; ) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next)) {
                return next;
            }
        }
    }
}
