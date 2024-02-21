package com.qy.res;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

public enum ResultCodeEnum {

    SUCCESS("00000", "接口调用成功"),
    FAIL("99999", "系统内部错误");

    @Getter
    private String code;
    @Getter
    private String message;


    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据代码获取ENUM
     */
    public static ResultCodeEnum getByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }

        for (ResultCodeEnum type : ResultCodeEnum.values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }


}
