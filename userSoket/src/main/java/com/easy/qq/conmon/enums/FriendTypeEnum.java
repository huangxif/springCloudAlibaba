package com.easy.qq.conmon.enums;

/**
 * 好友类型
 */
public enum FriendTypeEnum {
    /**
     * 我的好友
     */
    FRIEND(0, "普通好友"),

    /**
     * 黑名单
     */
    GROUP(1, "群组");


    /**
     * ID
     */
    private int code;
    /**
     * 类型描述
     */
    private String desc;

    FriendTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
