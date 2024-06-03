package com.easy.qq.web.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.easy.qq.entity.QqFriendGroup;
import com.easy.qq.entity.QqFriends;
import com.easy.qq.entity.QqUser;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QqFriendSessionVo {
    private static final long serialVersionUID = 1766022444339212363L;


    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer sid;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 好友ID
     */
    private Integer friendUserId;
    /**
     * 是否群组:1:不是,2是
     */
    private Integer sessionType;
    /**
     * 会话状态:0:删除,1显示,2隐藏,3 置顶
     */
    private Integer sessionStatus;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;


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
