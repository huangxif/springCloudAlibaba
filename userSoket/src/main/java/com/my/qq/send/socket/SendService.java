package com.my.qq.send.socket;

import com.my.qq.conmon.Result;
import com.my.qq.send.service.MySocketHandler;
import com.my.qq.send.vo.MessageVo;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SendService {
    @Resource
    private MySocketHandler socketHandler;


    /**
     * 发送单人消息
     *
     * @param messageVo
     */
    public Result<Void> sendMsg(MessageVo<String> messageVo) {
        //TODO 消息先存库
        Channel channel = socketHandler.CONCURRENT_HASH_MAP.get(messageVo.getTo());
        if (channel == null) {
            return new Result(true, "发送离线成功", "000000", null);
        }
        channel.writeAndFlush(new TextWebSocketFrame(messageVo.getData()));
        return new Result(true, "发送成功", "000000", null);
    }
}
