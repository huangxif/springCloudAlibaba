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
    private Integer typeId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 分组名称
     */
    private String typeName;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 排序
     */
    private Integer typeOrder;
    /**
     * 是否隐藏:0:不展示,1:展示
     */
    private Integer isShow;
    /**
     * 是否隐藏:0:系统创建,1:用户创建
     */
    private Integer isSys;
}

