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
     * 处理结果:1:拒绝，2:同意
     */
    @NotNull
    @Min(value = 1, message = "dealWith参数有误")
    @Max(value = 2, message = "dealWith参数有误")
    private Integer dealWith;

    /**
     * 昵称
     */
    private String friendNickName;


    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不可为空")
    private Integer userId;

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
