package com.easy.qq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务消息记录表(QqBusinessMessage)实体类
 *
 * @author makejava
 * @since 2024-06-03 22:21:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIEND_CHATTING_RECORDS")
public class QqBusinessMessage implements Serializable {
    private static final long serialVersionUID = -83545292174949284L;
    /**
     * 主键ID
     */
    private Integer messageId;
    /**
     * 成员ID
     */
    private Integer userId;
    /**
     * 发送方ID
     */
    private Integer fromId;
    /**
     * 接受方
     */
    private Integer toId;
    /**
     * 群组ID
     */
    private Integer gid;
    /**
     * 2:添加好友
     */
    private Integer businessType;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 扩展消息内容
     */
    private String messageExe;
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

