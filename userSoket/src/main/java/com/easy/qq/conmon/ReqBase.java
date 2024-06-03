package com.easy.qq.conmon;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 请求公共参数
 */
@Data
public class ReqBase {
    /**
     * 请求来源
     */
    @NotNull(message = "请求来源不可为空")
    @Min(message = "请求来源不正确", value = 1)
    @Max(message = "请求来源不正确", value = 2)
    private Integer source;
}
