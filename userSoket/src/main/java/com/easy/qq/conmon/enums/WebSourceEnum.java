package com.easy.qq.conmon.enums;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * 请求端来源
 */
public enum WebSourceEnum {


    /**
     * 我的好友
     */
    MOBILE(1, "MOBILE", "手机登录"),

    /**
     * 黑名单
     */
    COMPUTER(2, "COMPUTER", "电脑登录");


    /**
     * 值
     */
    private int value;
    /**
     * 编码
     */
    private String code;
    /**
     * 类型描述
     */
    private String desc;

    WebSourceEnum(int value, String code, String desc) {
        this.value = value;
        this.code = code;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    /**
     * 根据value匹配
     *
     * @param value
     * @return
     */
    public static WebSourceEnum getByValue(Integer value) {
        if (value == null || value < MOBILE.value || value > COMPUTER.value) {
            return null;
        }
        return Arrays.stream(WebSourceEnum.values()).filter(source -> value == source.value).findFirst().get();
    }

    /**
     * 根据code匹配
     *
     * @param code
     * @return
     */
    public static WebSourceEnum getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return Arrays.stream(WebSourceEnum.values()).filter(source -> source.code.equals(code)).findFirst().get();
    }
}
