package com.easy.qq.conmon.enums;

import java.util.Arrays;

/**
 * 消息类型
 */
public enum SendTypeEnum {
    /**
     * 注册消息
     */
    REGISTER_MSG(1, "注册消息"),
    /**
     * 好友消息
     */
    FRIEND_MSG(2, "好友消息"),
    /**
     * 群组消息
     */
    GROUP_MSG(3, "群组消息");
    /**
     * 消息类型
     */
    private int type;
    /**
     * 类型描述
     */
    private String desc;

    SendTypeEnum(int type, String desc) {
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
    public SendTypeEnum getSendTypeByType(Integer type) {
        //不在枚举区间的默认返回好友消息
        if (type == null || type < 1 || type > GROUP_MSG.getType()) {
            return FRIEND_MSG;
        }
        return Arrays.stream(SendTypeEnum.values()).filter(messageTypeEnum -> type == messageTypeEnum.getType()).findFirst().get();
    }
}
