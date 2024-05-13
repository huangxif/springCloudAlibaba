package com.easy.qq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息列表(QqFriendSession)实体类
 *
 * @author makejava
 * @since 2024-05-13 15:00:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIEND_SESSION")
public class QqFriendSession implements Serializable {
    private static final long serialVersionUID = -41932438598496803L;
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
    private Integer friendId;
    /**
     * 消息ID
     */
    private Integer cid;
    /**
     * 消息状态:1未读,2已读,3撤回
     */
    private Integer messageStatus;
    /**
     * 是否最后一条消息:0:不是,1是
     */
    private Integer lastMessage;
    /**
     * 是否群组:0:不是,1是
     */
    private Integer sessionType;
    /**
     * 会话状态:0:删除,1显示
     */
    private Integer sessionStatus;



}

