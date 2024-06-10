package com.easy.qq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.qq.entity.QqFriendSessionChattingRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会话消息关联表(QqFriendSessionChattingRelation)表数据库访问层
 *
 * @author makejava
 * @since 2024-05-14 10:16:55
 */
public interface QqFriendSessionChattingRelationMapper extends BaseMapper<QqFriendSessionChattingRelation> {
    /**
     * 更新最后一条消息标记
     *
     * @param toSessionId
     * @param fromSession
     * @return
     */

    int updateLastFlag(@Param("toSessionId") Integer toSessionId, @Param("fromSession") Integer fromSession);

    /**
     * 修改未读消息为已读
     *
     * @param userId
     * @param ids
     * @return
     */
    int updateByIds(@Param("userId") Integer userId, @Param("ids") List<Integer> ids);
}

