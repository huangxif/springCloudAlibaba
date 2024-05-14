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
 * 好友分组表(QqFriendsType)实体类
 *
 * @author makejava
 * @since 2024-05-14 10:15:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIENDS_TYPE")
public class QqFriendsType implements Serializable {
    private static final long serialVersionUID = 625833240488179633L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer tid;
    /**
     * 分组名称
     */
    private String typeName;
    /**
     * 添加时间
     */
    private Date createTime;




}

