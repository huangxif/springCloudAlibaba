package com.easy.qq.web.user.vo;

import com.easy.qq.entity.QqFriendChattingRecords;

public class ChattingRecordsVo extends QqFriendChattingRecords {
    private static final long serialVersionUID = 5128967271191956294L;

    private Integer crid;
    /**
     * 会话ID
     */
    private Integer sid;

    /**
     * 是否最后1条:0,不是最后1条，1是最后1条
     */
    private Integer isLast;

    /**
     * 是否未读:是否未读:0未读，1已读,2撤回
     */
    private Integer isRead;
}
