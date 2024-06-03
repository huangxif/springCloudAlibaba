package com.easy.qq.conmon.enums;

import java.util.Arrays;

public enum BusinessTypeEnum {

    /**
     * 普通消息
     */
    FRIEND_MESSAGE(1, "普通消息"),
    /**
     * 添加好友消息
     */
    ADD_FRIEND(2, "添加好友消息");

    /**
     * ID
     */
    private int code;
    /**
     * 类型描述
     */
    private String desc;

    BusinessTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据CODE查找
     *
     * @param code
     * @return
     */
    public static BusinessTypeEnum getByCode(int code) {
        if (code < FRIEND_MESSAGE.code || code > ADD_FRIEND.code) {
            return null;
        }
        return Arrays.stream(BusinessTypeEnum.values()).filter(type -> type.code == code).findFirst().get();
    }
}
