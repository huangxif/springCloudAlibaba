package com.qy.config;

import cn.hutool.core.collection.CollectionUtil;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static void main(String[] args) {
//        ScheduledExecutorService service= ExecutorServiceUtil.newScheduledExecutorService();
//        service.schedule();
        AtomicInteger i= new AtomicInteger();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
              {System.out.println(i.get());i.getAndIncrement();
            }
        }}, new Date(),10*100);
    }


}
