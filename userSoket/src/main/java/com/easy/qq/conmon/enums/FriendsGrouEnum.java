package com.easy.qq.conmon.enums;

/**
 * 默认好友分组ID
 */
public enum FriendsGrouEnum {
    /**
     * 特别关心
     */
    SPECIAL_FRIEND(0, "特别关心"),
    /**
     * 我的好友
     */
    MY_FRIEND(1, "我的好友"),
    /**
     * 黑名单
     */
    BLACKLIST(2, "黑名单");

    /**
     * 排序
     */
    private int order;
    /**
     * 类型描述
     */
    private String desc;

    FriendsGrouEnum(int order, String desc) {
        this.order = order;
        this.desc = desc;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


