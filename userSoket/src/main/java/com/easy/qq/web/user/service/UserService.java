package com.easy.qq.web.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.easy.qq.conmon.Result;
import com.easy.qq.conmon.enums.*;
import com.easy.qq.conmon.utils.RedisCacheUtil;
import com.easy.qq.entity.*;
import com.easy.qq.mapper.*;
import com.easy.qq.web.send.service.SendService;
import com.easy.qq.web.user.req.UserLoginReq;
import com.easy.qq.web.user.req.UserVo;
import com.easy.qq.web.user.res.UserLoginRes;
import com.easy.qq.web.user.vo.QqFriendSessionVo;
import com.easy.qq.web.user.vo.QqFriendsTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
     * 默认查询多少条消息记录
     */
    public static final int MESSAGE_LIMIT = 100;

    /**
     * redis token缓存键值
     */
    public static final String LOGIN_TOKEN_REDIS_KEY = "LOGIN_TOKEN_REDIS_KEY";

    /**
     * redis token缓存键值
     */
    public static final long LOGIN_TOKEN_REDIS_KEY_TIME = 60 * 60 * 24;

    /**
     * TOKEN_KE秘钥
     */
    public static final String TOKEN_KEY = "TOKEN_KEY@123456";

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
    @Resource
    private QqFriendsTypeMapper friendsTypeMapper;
    @Resource
    private QqFriendGroupMapper qqFriendGroupMapper;
    @Resource
    private RedisCacheUtil redisCacheUtil;

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
        QqFriendsTypeRelation myFriend = new QqFriendsTypeRelation(null, uid, FriendsGrouEnum.MY_FRIEND.getId(), new Date());
        QqFriendsTypeRelation blacklist = new QqFriendsTypeRelation(null, uid, FriendsGrouEnum.BLACKLIST.getId(), new Date());
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
        UserLoginRes res = new UserLoginRes();
        //登录校验密码
        QqUser user = getUser(req);
        res.setUser(user);
        res.setSessionList(getSessionList(user.getUid()));
        //查询联系人
        res.setQqFriendsTypeList(getQqFriendsType(user.getUid()));
        //TODO 个人设置详情
        //查询群聊
        LambdaQueryChainWrapper<QqFriends> lambdaQuery = new LambdaQueryChainWrapper(qqFriendsMapper);
        List<QqFriends> qqFriends = lambdaQuery.eq(QqFriends::getUserId, user.getUid()).eq(QqFriends::getFriendType, FriendTypeEnum.GROUP.getCode()).orderByDesc(QqFriends::getFid).list();
        res.setGroupFriends(qqFriends);
        JSONObject tokenMap = new JSONObject();
        tokenMap.put("userId", user.getUid());
        tokenMap.put("source", req.getSource());
        //生成token
        res.setToken(getToken(tokenMap));
        WebSourceEnum sourceEnum = WebSourceEnum.getByValue(req.getSource());
        redisCacheUtil.set(LOGIN_TOKEN_REDIS_KEY + "_" + sourceEnum.getCode() + "_" + user.getUid(), res.getToken(), LOGIN_TOKEN_REDIS_KEY_TIME);
        redisCacheUtil.set(LOGIN_TOKEN_REDIS_KEY + "_" + sourceEnum.getCode() + "_" + res.getToken(), user.getUid(), LOGIN_TOKEN_REDIS_KEY_TIME);
        return new Result(true, "登录成功", "200", res);
    }

    /**
     * 获取token
     *
     * @param map
     * @return
     */
    public static String getToken(JSONObject map) {
        String str = JWTUtil.createToken(map, TOKEN_KEY.getBytes(StandardCharsets.UTF_8));
        return str;
//        JWT jwt = JWTUtil.parseToken(str);
//        System.out.println(jwt.getPayload().getClaim("userId"));
    }

    /**
     * 查询联系人
     *
     * @param uid
     */
    private List<QqFriendsTypeVo> getQqFriendsType(Integer uid) {
        LambdaQueryChainWrapper<QqFriends> lambdaQuery = new LambdaQueryChainWrapper(qqFriendsMapper);
        List<QqFriendsTypeVo> vo = friendsTypeMapper.getQqFriendsType(uid);

        vo.stream().filter(typeVo -> typeVo.getFriendNum() > 0).forEach(typeVo -> {
            //TODO 好友昵称
            typeVo.setQqFriends(lambdaQuery.eq(QqFriends::getUserId, uid).eq(QqFriends::getRid, typeVo.getRid()).list());
        });
        return vo;
    }

    /**
     * 登录校验
     *
     * @param req
     * @return
     */
    private QqUser getUser(UserLoginReq req) {
        LambdaQueryChainWrapper<QqUser> lambdaQuery = new LambdaQueryChainWrapper(qqUserMapper);
        QqUser user;
        if (LoginTypeEnum.ACCOUNT_PASSWORD.getType() == req.getLoginType()) {
            user = lambdaQuery.eq(QqUser::getUid, req.getUserId()).getEntity();
        } else if (LoginTypeEnum.PHONE_CODE.getType() == req.getLoginType()) {
            user = lambdaQuery.eq(QqUser::getPhone, req.getPhone()).getEntity();
        } else if (LoginTypeEnum.EMAI_CODE.getType() == req.getLoginType()) {
            user = lambdaQuery.eq(QqUser::getEmai, req.getEmai()).getEntity();
        } else {
            throw new RuntimeException("不支持的登陆方式");
        }
        return user;
    }

    /**
     * 查询会话列表
     *
     * @param userId
     * @return
     */
    private List<QqFriendSessionVo> getSessionList(Integer userId) {
        LambdaQueryChainWrapper<QqFriends> friendsLambdaQuery = new LambdaQueryChainWrapper(qqFriendsMapper);
        LambdaQueryChainWrapper<QqFriendGroup> groupLambdaQuery = new LambdaQueryChainWrapper(qqFriendGroupMapper);
        LambdaQueryChainWrapper<QqUser> userLambdaQuery = new LambdaQueryChainWrapper(qqUserMapper);
        List<QqFriendSessionVo> vo = sessionMapper.getSessionList(userId);
        if (CollectionUtil.isEmpty(vo)) {
            return new ArrayList<>();
        }
        vo.forEach(item -> {
            //查询好友信息
            item.setFriend(friendsLambdaQuery.eq(QqFriends::getUserId, userId).eq(QqFriends::getFriendUserId, item.getFriendUserId()).getEntity());

            //查询好友具体信息
            if (FriendTypeEnum.FRIEND.getCode() == item.getSessionType()) {
                item.setFriendUser(userLambdaQuery.eq(QqUser::getUid, item.getFriendUserId()).getEntity());
            } else {
                item.setGroup(groupLambdaQuery.eq(QqFriendGroup::getGid, item.getFriendUserId()).getEntity());
            }
            //查询消息记录
            item.setMessageList(chattingRecordsMapper.getChattingRecords(null, item.getSid(), MESSAGE_LIMIT));
            if (CollectionUtil.isNotEmpty(item.getMessageList())) {
                Collections.reverse(item.getMessageList());
            }

        });
        return null;
    }
}
