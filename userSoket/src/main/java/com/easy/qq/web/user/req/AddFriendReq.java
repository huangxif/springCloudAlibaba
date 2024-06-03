package com.easy.qq.web.user.req;

import com.easy.qq.conmon.ReqBase;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
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
     * 是否群组:0:好友，1:群组
     */
    @NotNull(message = "friendType不可为空")
    @Min(value = 0, message = "friendType参数有误")
    @Max(value = 1, message = "friendType参数有误")
    private Integer friendType;


}
