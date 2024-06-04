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
 * 群组表(QqGroup)实体类
 *
 * @author makejava
 * @since 2024-05-13 15:01:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("QQ_GROUP")
public class QqGroup implements Serializable {
    private static final long serialVersionUID = -50956011659670050L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer gid;
    /**
     * 群名称
     */
    private Integer groupName;
    /**
     * 群介绍
     */
    private String groupDesc;
    /**
     * 群头像
     */
    private String groupPhoto;
    /**
     * 群创建人
     */
    private Integer groupCreateUser;
    /**
     * 创建时间
     */
    private Date groupCreateDate;
    /**
     * 修改时间
     */
    private Date groupUpdateDate;
}

