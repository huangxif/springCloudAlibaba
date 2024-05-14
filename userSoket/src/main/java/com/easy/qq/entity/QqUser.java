package com.easy.qq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(QqUser)实体类
 *
 * @author makejava
 * @since 2024-05-14 10:14:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_USER")
public class QqUser implements Serializable {
    private static final long serialVersionUID = 702864528146568094L;
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
     * 用户性别0:保密,1:男,2:女
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
     * 用户状态:0:注销,1正常,2:锁定
     */
    private Integer userStatus;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;




}

