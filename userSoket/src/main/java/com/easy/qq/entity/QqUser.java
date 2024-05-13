package com.easy.qq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(QqUser)实体类
 *
 * @author makejava
 * @since 2024-05-13 15:02:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_USER")
public class QqUser implements Serializable {
    private static final long serialVersionUID = 365503424201158523L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;
    /**
     * 用户昵称
     */
    private String userNickName;
    /**
     * 用户真实姓名
     */
    private String userName;
    /**
     * 用户性别0:未知,1:男,2:女
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 星座
     */
    private Integer constellation;
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
    private String phone;
    /**
     * 电子邮箱
     */
    private String emai;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户状态:
     */
    private Integer userStatus;


}

