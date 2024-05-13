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
 * 好友表(QqFriends)实体类
 *
 * @author makejava
 * @since 2024-05-13 14:59:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIENDS")
public class QqFriends implements Serializable {
    private static final long serialVersionUID = 875311806402306177L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer fid;
    /**
     * 好友备注
     */
    private String friendNickName;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 好友ID
     */
    private String friendUserId;
    /**
     * 好友分组
     */
    private Integer friendGroup;
    /**
     * 好友类型：1好友,2群聊
     */
    private Integer friendType;
    /**
     * 消息提醒类型
     */
    private Integer messageReminding;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;



}

