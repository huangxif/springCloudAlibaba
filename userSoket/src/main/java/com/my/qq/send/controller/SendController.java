package com.my.qq.send.controller;

import com.my.qq.conmon.Result;
import com.my.qq.send.socket.SendService;
import com.my.qq.send.vo.MessageVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/send")
public class SendController {
    @Resource
    private SendService sendService;

    /**
     * 发送单人消息
     *
     * @param messageVo
     */
    @PostMapping("one")
    public Result<Void> sendMsg(@RequestBody MessageVo<String> messageVo) {
        return sendService.sendMsg(messageVo);
    }
}
