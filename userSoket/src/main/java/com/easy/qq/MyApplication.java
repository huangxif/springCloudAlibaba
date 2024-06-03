package com.easy.qq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动器
 *
 * @author 17691127401
 */
@EnableAsync
@MapperScan("com.easy.qq.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class MyApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(MyApplication.class, args);
    }
}
