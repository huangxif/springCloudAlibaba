package com.easy.qq.web.send.vo;

import com.easy.qq.conmon.ReqBase;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SendPersonMsgVo extends ReqBase {
    /**
     * 消息内容
     */
    @NotNull(message = "msg不可为空")
    private String msg;
    /**
     * 消息接收方
     */
    @NotNull(message = "to不可为空")
    @Min(value = 1, message = "参数有误")
    private Integer to;
    /**
     * 发送方ID
     */
    @NotNull(message = "from不可为空")
    @Min(value = 1, message = "参数有误")
    private Integer from;
    /**
     * 是否群组:0:普通消息，1:群组消息:默认普通消息
     */
    private int isGroup = 0;
    /**
     * 消息类型:1文本消息,2:表情,3:文件消息
     */
    private String msgType;
    /**
     * 消息扩展内容
     */
    private Object object;

}
