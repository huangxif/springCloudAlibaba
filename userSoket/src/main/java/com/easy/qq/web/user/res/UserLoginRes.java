package com.easy.qq.web.user.res;

import com.easy.qq.entity.QqUser;
import lombok.Data;

/**
 * 登录返回实体类
 */
@Data
public class UserLoginRes {
    /**
     * user对象
     */
    private QqUser user;

    /**
     * token
     */
    private String token;
}
