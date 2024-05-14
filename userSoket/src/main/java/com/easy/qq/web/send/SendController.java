package com.easy.qq.web.send;

import com.easy.qq.conmon.Result;
import com.easy.qq.entity.QqFriendChattingRecords;
import com.easy.qq.web.send.service.SendService;
import com.easy.qq.web.send.vo.MessageVo;
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
    public Result<Void> sendMsg(@RequestBody QqFriendChattingRecords record) {
        return sendService.sendMsg(record);
    }
}
