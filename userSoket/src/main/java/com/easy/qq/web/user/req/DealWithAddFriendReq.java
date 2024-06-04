package com.easy.qq.web.user.req;

import com.easy.qq.conmon.ReqBase;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 处理添加好友请求
 */
@Data
@ToString(callSuper = true)
public class DealWithAddFriendReq extends ReqBase {
    /**
     * messageId 不可为空
     */
    @NotNull(message = "messageId不可为空")
    private Integer messageId;
    /**
     * 处理结果:0:拒绝，1:同意
     */
    @NotNull
    @Min(value = 0, message = "dealWith参数有误")
    @Max(value = 1, message = "dealWith参数有误")
    private Integer dealWith;

    /**
     * 好友分组ID
     */
    @NotNull
    @Min(value = 0, message = "dealWith参数有误")
    private Integer rid;


    /**
     * 昵称
     */
    private String friendNickName;
}
