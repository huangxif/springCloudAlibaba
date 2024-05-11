package com.easy.qq.send.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息内容
 */
@Data
public class MessageVo<T> implements Serializable {
    private static final long serialVersionUID = 5881048601971635180L;
    /**
     * 接收人
     */
    private String to;

    /**
     * 发送人
     */
    private String send;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息内容
     */
    private T data;

}
