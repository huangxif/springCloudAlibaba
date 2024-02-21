package com.qy.book.controller;

import com.qy.res.BaseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {
    @Value("${server.port}")
    String servicePort;
    @Value("${spring.application.name}")
    String serviceName;

    @GetMapping("/get")
    public BaseResult<String> getOrder() {
        return new BaseResult(true, "000000", "查询成功", "serviceName:" + serviceName + ",servicePort:" + servicePort);
    }
}
