package com.easy.qq.web.user.vo;

import com.easy.qq.entity.QqFriendGroup;
import com.easy.qq.entity.QqFriendSession;
import com.easy.qq.entity.QqFriends;
import com.easy.qq.entity.QqUser;
import lombok.Data;

import java.util.List;

@Data
public class QqFriendSessionVo extends QqFriendSession {
    private static final long serialVersionUID = 1766022444339212363L;
    /**
     * 好友信息
     */
    private QqFriends friend;
    /**
     * 好友用户信息
     */
    private QqUser friendUser;

    /**
     * 好友用户信息
     */
    private QqFriendGroup group;

    /**
     * 消息列表
     */
    private List<ChattingRecordsVo> messageList;
}
