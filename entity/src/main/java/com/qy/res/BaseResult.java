package com.qy.res;

import lombok.Data;

@Data
public class BaseResult<T>  {
    /**
     * 成功标识
     */
    private boolean success;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 状态码
     */
    private String resultCode;
    /**
     * 数据
     */
    private T data;
    /**
     * 数据总条数
     */
    private Long total;

    public BaseResult() {
    }

    public BaseResult(boolean success, ResultCodeEnum resultCodeEnum) {
        this.success = success;
        this.message = resultCodeEnum.getCode();
        this.resultCode = resultCodeEnum.getMessage();
    }

    public BaseResult(boolean success, String resultCode, String message) {
        this.success = success;
        this.message = message;
        this.resultCode = resultCode;
    }


    public BaseResult(boolean success, ResultCodeEnum resultCodeEnum, T data) {
        this.success = success;
        this.message = resultCodeEnum.getCode();
        this.resultCode = resultCodeEnum.getMessage();
        this.data = data;
    }

    public BaseResult(boolean success, String resultCode, String message, T data) {
        this.success = success;
        this.message = message;
        this.resultCode = resultCode;
        this.data = data;
    }

    public BaseResult(boolean success, ResultCodeEnum resultCodeEnum, T data, Long total) {
        this.success = success;
        this.message = resultCodeEnum.getCode();
        this.resultCode = resultCodeEnum.getMessage();
        this.data = data;
        this.total = total;
    }

    public BaseResult(boolean success, String resultCode, String message, T data, Long total) {
        this.success = success;
        this.message = message;
        this.resultCode = resultCode;
        this.data = data;
        this.total = total;
    }
}
