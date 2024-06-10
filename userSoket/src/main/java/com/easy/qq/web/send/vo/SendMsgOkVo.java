package com.easy.qq.web.send.vo;

import com.easy.qq.conmon.ReqBase;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 消息已读回执
 */
@Data
public class SendMsgOkVo extends ReqBase {
    /**
     * 用户ID
     */
    @NotNull(message = "userId不可为空")
    @Min(value = 1, message = "参数有误")
    private Integer userId;
    /**
     * 消息ID
     */
    @NotEmpty(message = "ids不可为空")
    private List<Integer> ids;

}
