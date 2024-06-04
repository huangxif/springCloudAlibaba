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
 * @since 2024-05-14 10:16:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIENDS")
public class QqFriends implements Serializable {
    private static final long serialVersionUID = 407139390157618051L;
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
    private Integer userId;
    /**
     * 好友ID
     */
    private Integer friendUserId;
    /**
     * 好友分组ID
     */
    private Integer typeId;
    /**
     * 好友类型：0好友,1群聊
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

