package com.qy.config;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRoleConfig {


    @Bean
    public IRule iRule() {
        return new MyRule();
    }



}
