package com.easy.qq.web.user.req;

import com.easy.qq.conmon.ReqBase;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 添加好友或者群聊
 */
@Data
@ToString(callSuper = true)
public class AddFriendReq extends ReqBase {
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不可为空")
    private Integer userId;
    /**
     * 好友ID
     */
    @NotNull(message = "好友ID不可为空")
    private Integer friendUserId;

    /**
     * message
     */
    @NotNull(message = "message不可为空")
    private String message;
    /**
     * 分组主键ID:0为新增，非0为已有
     */
    @NotNull(message = "分组主键ID不可为空")
    @Min(value = 0, message = "typeId参数有误")
    private Integer typeId;
    /**
     * 分组名称
     */
    private String typeName;
}
