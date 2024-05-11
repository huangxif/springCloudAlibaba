package com.easy.qq.conmon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /**
     * 请求是否成功
     */
    private boolean success;
    /**
     * 描述
     */
    private String message;
    /**
     * 状态码
     */
    private String code;
    /**
     * 数据
     */
    private T data;


}
