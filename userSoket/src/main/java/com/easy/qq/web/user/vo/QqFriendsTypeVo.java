package com.easy.qq.web.user.vo;

import com.easy.qq.entity.QqFriends;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QqFriendsTypeVo {
    private static final long serialVersionUID = 5126305750041603193L;

    /**
     * 主键ID
     */
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

    /**
     * 好友数量
     */
    private Integer friendNum;
    /**
     * 好友集合
     */
    private List<QqFriends> qqFriends;

}
