package com.easy.qq.config;

import com.easy.qq.send.socket.NettyServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NettyServerRun implements ApplicationRunner {
    @Resource
    private NettyServer nettyServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyServer.nettyServerStart();
    }
}
