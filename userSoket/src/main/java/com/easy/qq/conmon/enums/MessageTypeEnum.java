package com.easy.qq.conmon.enums;

import java.util.Arrays;

public enum MessageTypeEnum {
    /**
     * 文本消息
     */
    TEXT_MSG(1, "文本消息"),
    /**
     * 表情消息
     */
    FACE_MSG(2, "表情消息"),
    /**
     * 文件消息
     */
    FILE_MSG(3, "文件消息");
    /**
     * 消息类型
     */
    private int type;
    /**
     * 类型描述
     */
    private String desc;

    MessageTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据type获取枚举值
     *
     * @param type
     * @return
     */
    public MessageTypeEnum getMessageTypeByType(Integer type) {
        //不在枚举区间的默认返回文本消息
        if (type == null || type < 1 || type > FILE_MSG.getType()) {
            return TEXT_MSG;
        }
        return Arrays.stream(MessageTypeEnum.values()).filter(messageTypeEnum -> type == messageTypeEnum.getType()).findFirst().get();
    }
}
