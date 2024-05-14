package com.easy.qq.web.user.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.easy.qq.conmon.Result;
import com.easy.qq.conmon.enums.FriendsTypeEnum;
import com.easy.qq.conmon.enums.MessageRemindingEnum;
import com.easy.qq.conmon.enums.MessageTypeEnum;
import com.easy.qq.entity.QqFriendChattingRecords;
import com.easy.qq.entity.QqFriends;
import com.easy.qq.entity.QqFriendsTypeRelation;
import com.easy.qq.entity.QqUser;
import com.easy.qq.mapper.*;
import com.easy.qq.web.send.service.SendService;
import com.easy.qq.web.user.req.UserLoginReq;
import com.easy.qq.web.user.res.UserLoginRes;
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
    public static final String REGISTER_USER_MESSAGE = "userName,您好！欢迎来到easyQq,我是你的专属客服小贝";
    @Resource
    private QqUserMapper qqUserMapper;
    @Resource
    private QqFriendChattingRecordsMapper chattingRecordsMapper;
    @Resource
    private QqFriendSessionMapper sessionMapper;
    @Resource
    private QqFriendsTypeRelationMapper typeRelationMapper;
    @Resource
    private QqFriendsMapper qqFriendsMapper;
    @Resource
    private QqFriendSessionChattingRelationMapper chattingRelationMapper;
    @Resource
    private SendService sendService;


    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    public Result<UserVo> registerUser(UserVo user) {
        //1.查询是否存在
        LambdaQueryChainWrapper<QqUser> userLambdaQuery = new LambdaQueryChainWrapper(qqUserMapper);
        List<QqUser> users = userLambdaQuery.eq(QqUser::getPhone, user.getPhone()).or().eq(QqUser::getEmai, user.getEmai()).list();
        if (CollectionUtil.isNotEmpty(users)) {
            throw new RuntimeException("该手机号或者邮箱已经注册");
        }
        //2.保存用户表
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        int insert = qqUserMapper.insert(user);

        //3.添加默认分组:好友分组和黑名单
        int rid = addDefaultGroup(user.getUid());
        //4.添加机器人好友
        int robotId = addRobot(user.getUid(), rid);
        //5.发送机器人消息
        sendRobotMessage(user);
        //6.新建成功 返回
        return new Result(true, "注册成功", "200", user);
    }

    /**
     * 添加默认分组并返回我的好友分组ID
     *
     * @return
     */
    private int addDefaultGroup(Integer uid) {
        QqFriendsTypeRelation myFriend = new QqFriendsTypeRelation(null, uid, FriendsTypeEnum.MY_FRIEND.getId(), new Date());
        QqFriendsTypeRelation blacklist = new QqFriendsTypeRelation(null, uid, FriendsTypeEnum.BLACKLIST.getId(), new Date());
        typeRelationMapper.insert(myFriend);
        typeRelationMapper.insert(blacklist);
        return myFriend.getRid();
    }

    /**
     * 添加机器人,并返回ID
     *
     * @return
     */
    private int addRobot(Integer uid, Integer rid) {
        QqFriends robot = new QqFriends(null, null, uid, UID, rid, 1, MessageRemindingEnum.COMMON.getType(), new Date(), new Date());
        qqFriendsMapper.insert(robot);
        return robot.getFid();
    }

    /**
     * 发送机器人消息
     *
     * @return
     */
    private void sendRobotMessage(UserVo vo) {
        //1.发送欢迎消息
        QqFriendChattingRecords record = new QqFriendChattingRecords();
        record.setToId(vo.getUid());
        record.setFromId(UID);
        //TODO 参数配置替换姓名
        record.setChattingText(REGISTER_USER_MESSAGE);
        record.setCreateDate(new Date());
        record.setCreateTime(record.getCreateDate().getTime());
        record.setMessageType(MessageTypeEnum.TEXT_MSG.getType());
        record.setMessageGroup(1);
        sendService.sendMsg(record);
    }

    /**
     * 登录
     *
     * @param req
     * @return
     */
    public Result<UserLoginRes> userLogin(UserLoginReq req) {
//        if(req)
        return null;
    }
}
