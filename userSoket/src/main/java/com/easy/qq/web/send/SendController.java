package com.easy.qq.web.send;

import com.easy.qq.conmon.Result;
import com.easy.qq.entity.QqFriendChattingRecords;
import com.easy.qq.web.send.service.SendService;
import com.easy.qq.web.send.vo.SendMsgOkVo;
import com.easy.qq.web.send.vo.SendPersonMsgVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/send")
public class SendController {
    @Resource
    private SendService sendService;

    /**
     * 发送单人消息
     *
     * @param record
     */
    @PostMapping("one")
    public Result<Void> sendMsg(@Valid @RequestBody QqFriendChattingRecords record) {
        return sendService.sendMsg(record, null);
    }

    /**
     * 发送单人消息
     *
     * @param sendMsgVo
     */
    @PostMapping("/one/msg")
    public Result<Void> sendPersonMsg(@Valid @RequestBody SendPersonMsgVo sendMsgVo) {
        return sendService.sendPersonMsg(sendMsgVo);
    }

    /**
     * 消息已读回执
     *
     * @param vo
     */
    @PostMapping("/msg/ok")
    public Result<Void> sendMsgOk(@Valid @RequestBody SendMsgOkVo vo) {
        return sendService.sendMsgOk(vo);
    }
}
