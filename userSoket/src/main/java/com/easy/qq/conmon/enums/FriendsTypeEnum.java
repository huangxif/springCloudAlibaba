package com.easy.qq.conmon.enums;

/**
 * 默认好友分组ID
 */
public enum FriendsTypeEnum {
    /**
     * 我的好友
     */
    MY_FRIEND(1, "我的好友"),
    /**
     * 黑名单
     */
    BLACKLIST(2, "黑名单");

    /**
     * ID
     */
    private int id;
    /**
     * 类型描述
     */
    private String desc;

    FriendsTypeEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }


    public String getDesc() {
        return desc;
    }

}
