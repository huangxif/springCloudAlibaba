package com.easy.qq.web.user.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserRegisterReq {

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不可为空")
    private String userNickName;
    /**
     * 用户真实姓名
     */
    private String userName;
    /**
     * 用户性别0:保密,1:男,2:女
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 现居住地
     */
    private String address;
    /**
     * 故乡
     */
    private String hometown;
    /**
     * 头像
     */
    private String picture;
    /**
     * 个性说明
     */
    private String signature;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不可为空")
    private String phone;
    /**
     * 电子邮箱
     */
    @NotBlank(message = "电子邮箱不可为空")
    private String emai;
    /**
     * 密码
     */
    private String password;

}
