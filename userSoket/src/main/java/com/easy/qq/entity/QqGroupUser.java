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
 * 群组成员表(QqFriendGroupUser)实体类
 *
 * @author makejava
 * @since 2024-05-13 15:06:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIEND_GROUP_USER")
public class QqGroupUser implements Serializable {
    private static final long serialVersionUID = -51247944217632999L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer guid;
    /**
     * 成员ID
     */
    private Integer userId;
    /**
     * 群ID
     */
    private Integer gid;
    /**
     * 0:普通成员，1:群主，2:群管理员
     */
    private Integer groupUserGrade;
    /**
     * 我在群备注
     */
    private String myGroupName;
    /**
     * 创建时间
     */
    private Date addGroupDate;



}

