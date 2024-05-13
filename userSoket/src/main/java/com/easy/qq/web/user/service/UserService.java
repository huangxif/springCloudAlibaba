package com.easy.qq.web.user.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.easy.qq.conmon.MessageTypeEnum;
import com.easy.qq.conmon.Result;
import com.easy.qq.entity.QqFriendChattingRecords;
import com.easy.qq.entity.QqFriendSession;
import com.easy.qq.entity.QqUser;
import com.easy.qq.mapper.QqFriendChattingRecordsMapper;
import com.easy.qq.mapper.QqFriendSessionMapper;
import com.easy.qq.mapper.QqUserMapper;
import com.easy.qq.web.user.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService {
    /**
     * 机器人UID
     */
    public static final Integer UID = 1;
    /**
     * 注册欢迎消息
     */
    public static final String REGISTER_USER_MESSAGE = "欢迎来到easyQq,我是你的专属客服小贝";
    @Resource
    private QqUserMapper qqUserMapper;
    @Resource
    private QqFriendChattingRecordsMapper chattingRecordsMapper;
    @Resource
    private QqFriendSessionMapper sessionMapper;
    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    public Result<UserVo> registerUser(UserVo user) {
        LambdaQueryChainWrapper<QqUser> userLambdaQuery = new LambdaQueryChainWrapper(qqUserMapper);
        List<QqUser> users = userLambdaQuery.eq(QqUser::getPhone, user.getPhone()).or().eq(QqUser::getEmai, user.getEmai()).list();
        if (CollectionUtil.isNotEmpty(users)) {
            throw new RuntimeException("该手机号或者邮箱已经注册");
        }
        //TODO 没有注册时间和修改时间
        int insert = qqUserMapper.insert(user);
        Date createTime=new Date();
        //注册成功，默认发送一条机器人消息:
        QqFriendChattingRecords record = new QqFriendChattingRecords();
        record.setToId(user.getUid());
        record.setFromId(UID);
        record.setChattingText(REGISTER_USER_MESSAGE);
        record.setCreateTime(createTime.getTime());
        record.setCreateDate(createTime);
        chattingRecordsMapper.insert(record);
        //新键会话
        QqFriendSession friendSession=new QqFriendSession(user.getUid(),);
        sessionMapper.insert();
        return null;
    }
}
