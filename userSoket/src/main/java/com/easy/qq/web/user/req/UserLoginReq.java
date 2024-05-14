package com.easy.qq.web.user.req;

import com.easy.qq.conmon.ReqBase;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 登录请求实体
 */
@Data
@ToString(callSuper = true)
public class UserLoginReq extends ReqBase {
    /**
     * 登录方式:
     */
    @NotNull(message = "参数不可为空")
    private Integer loginType;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String emai;
    /**
     * 密码
     */
    private String password;

    public static void main(String[] args) {
        UserLoginReq req = new UserLoginReq();
        req.setSource(1);
        System.out.println(req);
    }

}
