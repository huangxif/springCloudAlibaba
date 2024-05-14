package com.easy.qq.conmon.enums;

import java.util.Arrays;

/**
 * 登录方式
 */
public enum LoginTypeEnum {

    /**
     * 账号密码
     */
    account_password(1, "账号密码"),
    /**
     * 手机验证码
     */
    PHONE_CODE(2, "手机验证码"),
    /**
     * 邮箱验证码
     */
    EMAI_CODE(3, "邮箱验证码");
    /**
     * 消息类型
     */
    private int type;
    /**
     * 类型描述
     */
    private String desc;

    LoginTypeEnum(int type, String desc) {
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
    public LoginTypeEnum getLoginTypeByType(Integer type) {
        //不在枚举区间的默认返回文本消息
        if (type == null || type < 1 || type > EMAI_CODE.getType()) {
            return null;
        }
        return Arrays.stream(LoginTypeEnum.values()).filter(loginTypeEnum -> type == loginTypeEnum.getType()).findFirst().get();
    }
}
