package com.easy.qq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会话消息关联表(QqFriendSessionChattingRelation)实体类
 *
 * @author makejava
 * @since 2024-05-14 10:16:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIEND_SESSION_CHATTING_RELATION")
public class QqFriendSessionChattingRelation implements Serializable {
    private static final long serialVersionUID = 426408255742558548L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer crid;
    /**
     * 消息ID
     */
    private Integer cid;
    /**
     * 会话ID
     */
    private Integer sid;
    /**
     * 是否最后1条:0,不是最后1条，1是最后1条
     */
    private Integer isLast;
    /**
     * 是否未读:0未读，1已读,2撤回
     */
    private Integer isRead;

}

