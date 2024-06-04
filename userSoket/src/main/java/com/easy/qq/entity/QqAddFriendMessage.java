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
 * 业务消息记录表(QqAddFriendMessage)实体类
 *
 * @author makejava
 * @since 2024-06-03 22:21:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_ADD_FRIEND_MESSAGE")
public class QqAddFriendMessage implements Serializable {
    private static final long serialVersionUID = -83545292174949284L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer messageId;
    /**
     * 被添加用户ID
     */
    private Integer userId;
    /**
     * 添加用户ID
     */
    private Integer fromId;
    /**
     * 群组ID
     */
    private Integer gid;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 好友备注
     */
    private String friendNickName;
    /**
     * 好友分组ID
     */
    private Integer typeId;
    /**
     * 是否读取:0:未读，1已读
     */
    private Integer isRead;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;


}

