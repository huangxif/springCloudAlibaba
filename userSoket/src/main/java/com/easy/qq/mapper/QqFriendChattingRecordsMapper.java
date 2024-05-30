package com.easy.qq.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.qq.entity.QqFriendChattingRecords;
import com.easy.qq.web.user.vo.ChattingRecordsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息记录表(QqFriendChattingRecords)表数据库访问层
 *
 * @author makejava
 * @since 2024-05-13 15:05:51
 */
public interface QqFriendChattingRecordsMapper extends BaseMapper<QqFriendChattingRecords> {

    /**
     * 查询消息列表
     *
     * @param sid
     * @param messageLimit
     * @return
     */
    List<ChattingRecordsVo> getChattingRecords(@Param("cid") Integer cid, @Param("sid") Integer sid, @Param("messageLimit") Integer messageLimit);
}

