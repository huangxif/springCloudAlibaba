package com.easy.qq.web.user.res;

import com.easy.qq.entity.QqFriends;
import com.easy.qq.entity.QqUser;
import com.easy.qq.web.user.vo.QqFriendSessionVo;
import com.easy.qq.web.user.vo.QqFriendsTypeVo;
import lombok.Data;

import java.util.List;

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
     * 会话列表
     */
    private List<QqFriendSessionVo> sessionList;

    /**
     * 好友分组
     */
    private List<QqFriendsTypeVo> qqFriendsTypeList;
    /**
     * 群聊
     */
    private List<QqFriends> groupFriends;

    /**
     * token
     */
    private String token;
}
