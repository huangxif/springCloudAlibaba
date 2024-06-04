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
 * 消息列表(QqFriendSession)实体类
 *
 * @author makejava
 * @since 2024-05-14 10:28:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIEND_SESSION")
public class QqFriendSession implements Serializable {
    private static final long serialVersionUID = 488305411486279534L;
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
     * 是否群组:0:不是,1是
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

}

