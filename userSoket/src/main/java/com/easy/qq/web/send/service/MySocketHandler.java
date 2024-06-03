package com.easy.qq.web.send.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.jwt.JWTPayload;
import com.easy.qq.conmon.enums.WebSourceEnum;
import com.easy.qq.conmon.utils.RedisCacheUtil;
import com.easy.qq.conmon.utils.StringUtil;
import com.easy.qq.web.user.service.UserService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
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
    public final ConcurrentHashMap<Integer, Map<String, Channel>> CONCURRENT_HASH_MAP = new ConcurrentHashMap();


    @Resource
    private RedisCacheUtil redisCacheUtil;


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //断开连接
        Channel channel = ctx.channel();
        AttributeKey<String> attributeKey = AttributeKey.valueOf(USER_TOKEN_KEY);
        String userToken = channel.attr(attributeKey).get();
        removeChannel(userToken);
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
            String source = handshakeComplete.requestHeaders().get("source");
            WebSourceEnum sourceEnum = WebSourceEnum.getByValue(Integer.valueOf(source));
            if (userToken == null || sourceEnum == null) {
                channel.close();
            }
            String userId = checkToken(userToken, sourceEnum);

            if (StringUtil.isNotEmpty(userId)) {
                AttributeKey<String> attributeKey = AttributeKey.valueOf(USER_TOKEN_KEY);
                channel.attr(attributeKey).set(userToken);
                Map<String, Channel> channelMap;

                if (CONCURRENT_HASH_MAP.containsKey(Integer.valueOf(userId))) {
                    channelMap = CONCURRENT_HASH_MAP.get(Integer.valueOf(userId));
                } else {
                    channelMap = new HashMap<>();
                    CONCURRENT_HASH_MAP.put(Integer.valueOf(userId), channelMap);
                }
                channelMap.put(sourceEnum.getCode(), ctx.channel());
            } else {
                channel.close();
            }


        }
    }

    public String checkToken(String token, WebSourceEnum sourceEnum) {
        try {
            JWTPayload payload = UserService.decodeJwtToken(token);
            String userIdObj = (String) payload.getClaim("userId");
            String sourceObj = (String) payload.getClaim("source");
            if (sourceEnum.getValue() != Integer.valueOf(sourceObj)) {
                return null;
            }
            String key = UserService.LOGIN_TOKEN_REDIS_KEY + "_" + sourceEnum.getCode() + "_" + token;
            String userId = redisCacheUtil.get(key);
            if (StringUtil.isNotEmpty(userId) && userId.equals(userIdObj)) {
                return userId;
            }
        } catch (Exception e) {
            log.error("checkToken,异常:", e);
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常移除连接
        AttributeKey<String> attributeKey = AttributeKey.valueOf(USER_TOKEN_KEY);
        String userToken = ctx.channel().attr(attributeKey).get();
        log.error("exceptionCaught:userToken{}关闭成功", userToken);
        removeChannel(userToken);
        log.error("exceptionCaught:", cause);
    }

    /**
     * 移除通道
     *
     * @param token
     */
    private void removeChannel(String token) {
        JWTPayload payload = UserService.decodeJwtToken(token);
        Integer userId = Integer.valueOf(payload.getClaim("userId").toString());
        WebSourceEnum source = WebSourceEnum.getByValue(Integer.valueOf(payload.getClaim("source").toString()));
        Map<String, Channel> channelMap = CONCURRENT_HASH_MAP.get(Integer.valueOf(userId));
        if (channelMap == null) {
            return;
        }
        channelMap.remove(source.getCode());
        //没有连接了，整个key移除，释放空间
        if (CollectionUtil.isEmpty(channelMap)) {
            CONCURRENT_HASH_MAP.remove(userId);
        }
        log.info("removeChannel,userId={},source={}端通道被移除", userId, source.getCode());
    }

}
