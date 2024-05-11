package com.easy.qq.send.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * socket 处理器
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class MySocketHandler extends ChannelInboundHandlerAdapter {
    /**
     * tokenKey
     */
    public static final String USER_TOKEN_KEY = "USER_TOKEN_KEY";


    /**
     * 存储连接
     */
    public final ConcurrentHashMap<String, Channel> CONCURRENT_HASH_MAP = new ConcurrentHashMap();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //断开连接
        Channel channel = ctx.channel();
        AttributeKey<String> attributeKey = AttributeKey.valueOf(USER_TOKEN_KEY);
        String userToken = channel.attr(attributeKey).get();
        CONCURRENT_HASH_MAP.remove(userToken);
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelActive:");
//        Channel channel = ctx.channel();
//        AttributeKey<String> attributeKey = AttributeKey.valueOf(USER_TOKEN_KEY);
//        String userToken = channel.attr(attributeKey).get();
//        //建立连接
//
//    }


//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//       if(msg.)
//    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered:");
        Channel channel = ctx.channel();
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String userToken = handshakeComplete.requestHeaders().get("userToken");
            if (userToken == null) {
                channel.close();
            }
            AttributeKey<String> attributeKey = AttributeKey.valueOf(USER_TOKEN_KEY);
            channel.attr(attributeKey).set(userToken);
            CONCURRENT_HASH_MAP.put(userToken, ctx.channel());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常移除连接
        AttributeKey<String> attributeKey = AttributeKey.valueOf(USER_TOKEN_KEY);
        String userToken = ctx.channel().attr(attributeKey).get();
        log.error("exceptionCaught:userToken{}关闭成功", userToken);
        if (userToken != null && CONCURRENT_HASH_MAP.containsKey(userToken)) {
            CONCURRENT_HASH_MAP.remove(userToken);
        }
        log.error("exceptionCaught:", cause);
    }


}
