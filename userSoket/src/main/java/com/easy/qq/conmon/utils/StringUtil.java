package com.easy.qq.conmon.utils;

import org.springframework.util.StringUtils;

/**
 * 字符串工具类
 */
public class StringUtil extends StringUtils {

    /**
     * 判断非空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


}
