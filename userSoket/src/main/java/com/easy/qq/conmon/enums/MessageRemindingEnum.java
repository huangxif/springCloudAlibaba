package com.easy.qq.conmon.enums;

/**
 * 消息提醒类型
 */
public enum MessageRemindingEnum {

    /**
     * 注册消息
     */
    COMMON(1, "普通提醒"),
    /**
     * 好友消息
     */
    ESPECIALLY(2, "特殊提醒");

    /**
     * 消息提醒类型
     */
    private int type;
    /**
     * 类型描述
     */
    private String desc;

    MessageRemindingEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
