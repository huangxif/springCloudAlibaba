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
 * 消息记录表(QqFriendChattingRecords)实体类
 *
 * @author makejava
 * @since 2024-05-13 15:05:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIEND_CHATTING_RECORDS")
public class QqFriendChattingRecords implements Serializable {
    private static final long serialVersionUID = -83601892155683453L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer cid;
    /**
     * 消息接受人
     */
    private Integer toId;
    /**
     * 消息发起人
     */
    private Integer fromId;
    /**
     * 消息内容
     */
    private String chattingText;
    /**
     * 消息发送时间时间戳
     */
    private Long createTime;
    /**
     * 消息发送时间
     */
    private Date createDate;
    /**
     * 消息类型:1文本消息,2:表情,3:文件消息
     */
    private Integer messageType;
    /**
     * 消息扩展数据
     */
    private String messageExe;
    /**
     * 是否群聊消息:1好友消息,2群聊消息
     */
    private Integer messageGroup;
    /**
     * 0无艾特,1艾特
     */
    private Integer messageAt;
    /**
     * -1无艾特，0艾特全部，其他艾特具体的人
     */
    private Integer messageAtId;


}

