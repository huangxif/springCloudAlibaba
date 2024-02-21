package com.qy.order.client;

import com.qy.config.MyRoleConfig;
import com.qy.res.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "book-service", path = "book",configuration = MyRoleConfig.class)
public interface BookClient {


    @GetMapping("/get")
    BaseResult<String> getOrder();
}
