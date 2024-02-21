package com.qy.config;

import cn.hutool.core.collection.CollectionUtil;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class MyRule  extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        System.out.println("key:" + key);
        List<Server> allServers = super.getLoadBalancer().getReachableServers();
        if (CollectionUtil.isNotEmpty(allServers)) {
            allServers.forEach(item -> {
                System.out.println("Server:" + item);
            });
        }
        return allServers.get(0);
    }

}
