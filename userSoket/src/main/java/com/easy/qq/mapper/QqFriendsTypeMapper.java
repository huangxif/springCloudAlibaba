package com.easy.qq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.qq.entity.QqFriendsType;
import com.easy.qq.web.user.vo.QqFriendsTypeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友分组表(QqFriendsType)表数据库访问层
 *
 * @author makejava
 * @since 2024-05-14 10:15:57
 */
public interface QqFriendsTypeMapper extends BaseMapper<QqFriendsType> {

    /**
     * 查询
     * @param uid
     * @return
     */
    List<QqFriendsTypeVo> getQqFriendsType(@Param("uid") Integer uid);
}

