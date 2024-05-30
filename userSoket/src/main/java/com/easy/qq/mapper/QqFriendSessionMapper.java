package com.easy.qq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.qq.entity.QqFriendSession;
import com.easy.qq.web.user.vo.QqFriendSessionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会话消息表(QqFriendSession)表数据库访问层
 *
 * @author makejava
 * @since 2024-05-14 10:16:55
 */
public interface QqFriendSessionMapper extends BaseMapper<QqFriendSession> {
    /**
     * 查询会话列表
     *
     * @param userId
     * @return
     */
    List<QqFriendSessionVo> getSessionList(@Param("userId") Integer userId);
}
