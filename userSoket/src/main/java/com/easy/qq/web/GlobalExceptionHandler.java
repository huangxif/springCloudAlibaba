package com.easy.qq.web;

import com.easy.qq.conmon.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final static String SYSTEM_EXCEPTION = "系统异常，请联系管理员";
    private final static int NOT_LOGIN_EXCEPTION_CODE = 50001;

    /**
     * 异常处理
     *
     * @param e 异常信息
     * @return 返回类是我自定义的接口返回类，参数是返回码和返回结果，异常的返回结果为空字符串
     */
    @ExceptionHandler(value = Exception.class)
    public Object handle(Exception e) {
        //自定义异常返回对应编码
//        log.error("请求路径:url={}", request.getRequestURI());
        if (e instanceof RuntimeException) {
            log.error("[运行时异常]{}", e.getMessage(), e);
            return new Result(false, e.getMessage(), "0203", null);
        } else {
            log.error("[系统异常]{}", e.getMessage(), e);
            return new Result(false, "系统异常", "0203", null);
        }

    }

    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = org.springframework.validation.BindException.class)
    public Result handleBindException(BindException e) {
        log.error("[参数绑定校验异常]{}", e);

        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验(Valid)异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleValidException(MethodArgumentNotValidException e) {
        log.error("[参数绑定校验异常]{}", e);

        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private Result wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
//            if (error instanceof FieldError) {
//                msg.append(((FieldError) error).getField()).append(": ");
//            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return new Result(false, msg.substring(2), "0203", null);
    }
}
