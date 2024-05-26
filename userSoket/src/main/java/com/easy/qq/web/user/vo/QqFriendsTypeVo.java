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
    private Integer tid;
    /**
     * 分组名称
     */
    private String typeName;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 关系ID
     */
    private Integer rid;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 好友数量
     */
    private Integer friendNum;
    /**
     * 好友集合
     */
    private List<QqFriends> qqFriends;

}
