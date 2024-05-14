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
 * 好友分组关系表(QqFriendsTypeRelation)实体类
 *
 * @author makejava
 * @since 2024-05-14 10:15:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_FRIENDS_TYPE_RELATION")
public class QqFriendsTypeRelation implements Serializable {
    private static final long serialVersionUID = 891483819769200473L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer rid;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 分组ID
     */
    private Integer tid;
    /**
     * 添加时间
     */
    private Date createTime;


}

