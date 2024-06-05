package com.easy.qq.web.send.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息内容
 */
@Data
public class MessageVo<T> implements Serializable {
    private static final long serialVersionUID = 5881048601971635180L;
    /**
     * 消息ID
     */
    private Integer msgId;
    /**
     * 接收人
     */
    private Integer to;

    /**
     * 发送人
     */
    private Integer send;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息内容
     */
    private T data;

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
