package com.easy.qq.web.user.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.easy.qq.conmon.Result;
import com.easy.qq.conmon.enums.*;
import com.easy.qq.conmon.utils.RedisCacheUtil;
import com.easy.qq.conmon.utils.StringUtil;
import com.easy.qq.entity.*;
import com.easy.qq.mapper.*;
import com.easy.qq.web.send.service.SendService;
import com.easy.qq.web.user.req.AddFriendReq;
import com.easy.qq.web.user.req.DealWithAddFriendReq;
import com.easy.qq.web.user.req.UserLoginReq;
import com.easy.qq.web.user.req.UserRegisterReq;
import com.easy.qq.web.user.res.UserLoginRes;
import com.easy.qq.web.user.vo.QqFriendSessionVo;
import com.easy.qq.web.user.vo.QqFriendsTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private QqFriendsMapper qqFriendsMapper;
    @Resource
    private SendService sendService;
    @Resource
    private QqFriendsTypeMapper friendsTypeMapper;
    @Resource
    private QqGroupMapper qqFriendGroupMapper;
    @Resource
    private RedisCacheUtil redisCacheUtil;
    @Resource
    private QqAddFriendMessageMapper addFriendMessageMapper;

    /**
     * 注册用户
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result<UserRegisterReq> registerUser(UserRegisterReq req) {
        //1.查询是否存在
        LambdaQueryChainWrapper<QqUser> userLambdaQuery = ChainWrappers.lambdaQueryChain(QqUser.class);
        List<QqUser> users = userLambdaQuery.eq(QqUser::getPhone, req.getPhone()).list();
        if (CollectionUtil.isNotEmpty(users)) {
            throw new RuntimeException("该手机号已经注册");
        }
        QqUser user = new QqUser();

        BeanUtil.copyProperties(req, user);
        if (user.getSex() == null) {
            user.setSex(0);
        }
        if (StringUtil.isEmpty(user.getPassword())) {
            user.setPassword("pass@" + user.getPhone().substring(6));
        }
        //todo 枚举
        user.setUserStatus(1);
        //2.保存用户表
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        int insert = qqUserMapper.insert(user);
        //3.添加默认分组
        int typeId = addDefaultType(user.getUid());
        //4.添加机器人好友
        int robotId = addRobot(user.getUid(), typeId);
        //5.发送机器人消息
        sendRobotMessage(user);
        //6.新建成功 返回
        return new Result(true, "注册成功", "200", user);
    }

    /**
     * 添加默认分组
     *
     * @param uid
     */
    private int addDefaultType(Integer uid) {
        QqFriendsType specialFriend = new QqFriendsType(null, uid, FriendsGrouEnum.SPECIAL_FRIEND.getDesc(), new Date(), new Date(), FriendsGrouEnum.SPECIAL_FRIEND.getOrder(), null, 0);
        friendsTypeMapper.insert(specialFriend);
        QqFriendsType blacklist = new QqFriendsType(null, uid, FriendsGrouEnum.BLACKLIST.getDesc(), new Date(), new Date(), FriendsGrouEnum.BLACKLIST.getOrder(), null, 0);
        friendsTypeMapper.insert(blacklist);
        QqFriendsType myFriend = new QqFriendsType(null, uid, FriendsGrouEnum.MY_FRIEND.getDesc(), new Date(), new Date(), FriendsGrouEnum.MY_FRIEND.getOrder(), null, 0);
        friendsTypeMapper.insert(myFriend);
        return myFriend.getTypeId();
    }


    /**
     * 添加机器人,并返回ID
     *
     * @return
     */
    private int addRobot(Integer uid, Integer typeId) {
        QqFriends robot = new QqFriends(null, null, uid, UID, typeId, FriendTypeEnum.FRIEND.getCode(), MessageRemindingEnum.COMMON.getType(), new Date(), new Date());
        qqFriendsMapper.insert(robot);
        return robot.getFid();
    }

    /**
     * 发送机器人消息
     *
     * @return
     */
    private void sendRobotMessage(QqUser vo) {
        //1.发送欢迎消息
        QqFriendChattingRecords record = new QqFriendChattingRecords();
        record.setToId(vo.getUid());
        record.setFromId(UID);
        //TODO 参数配置替换姓名
        record.setChattingText(REGISTER_USER_MESSAGE);
        record.setCreateDate(new Date());
        record.setCreateTime(record.getCreateDate().getTime());
        record.setMessageType(MessageTypeEnum.TEXT_MSG.getType());
        sendService.sendMsg(record, BusinessTypeEnum.FRIEND_MESSAGE);
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
        LambdaQueryChainWrapper<QqFriends> lambdaQuery = ChainWrappers.lambdaQueryChain(QqFriends.class);
        List<QqFriends> qqFriends = lambdaQuery.eq(QqFriends::getUserId, user.getUid()).eq(QqFriends::getFriendType, FriendTypeEnum.GROUP.getCode()).orderByDesc(QqFriends::getFid).list();
        res.setGroupFriends(qqFriends);
        JSONObject tokenMap = new JSONObject();
        tokenMap.put("userId", user.getUid() + "");
        tokenMap.put("source", req.getSource() + "");
        //生成token
        res.setToken(getJwtToken(tokenMap));
        WebSourceEnum sourceEnum = WebSourceEnum.getByValue(req.getSource());
        redisCacheUtil.set(LOGIN_TOKEN_REDIS_KEY + "_" + sourceEnum.getCode() + "_" + user.getUid(), res.getToken(), LOGIN_TOKEN_REDIS_KEY_TIME);
        //todo 校验请求端来源是否重复登录
        redisCacheUtil.set(LOGIN_TOKEN_REDIS_KEY + "_" + sourceEnum.getCode() + "_" + res.getToken(), user.getUid(), LOGIN_TOKEN_REDIS_KEY_TIME);
        return new Result(true, "登录成功", "200", res);
    }

    /**
     * 获取token
     *
     * @param map
     * @return
     */
    public static String getJwtToken(JSONObject map) {
        String str = JWTUtil.createToken(map, TOKEN_KEY.getBytes(StandardCharsets.UTF_8));
        return str;
//        JWT jwt = JWTUtil.parseToken(str);
//        System.out.println(jwt.getPayload().getClaim("userId"));
    }

    /**
     * 获取token
     *
     * @param token
     * @return
     */
    public static JWTPayload decodeJwtToken(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            JWTPayload payload = jwt.getPayload();
            if (payload == null) {
                throw new RuntimeException("token有误");
            }
            return payload;
        } catch (Exception e) {
            log.error("decodeJwtToken,jwtToken解析异常", e);
            throw new RuntimeException("token解析异常");
        }

    }


    /**
     * 查询联系人
     *
     * @param uid
     */
    private List<QqFriendsTypeVo> getQqFriendsType(Integer uid) {
        LambdaQueryChainWrapper<QqFriends> lambdaQuery = ChainWrappers.lambdaQueryChain(QqFriends.class);
        List<QqFriendsTypeVo> vo = friendsTypeMapper.getFriendsType(uid);

        vo.stream().filter(typeVo -> typeVo.getFriendNum() > 0).forEach(typeVo -> {
            //TODO 好友昵称
            typeVo.setQqFriends(lambdaQuery.eq(QqFriends::getUserId, uid).eq(QqFriends::getTypeId, typeVo.getTypeId()).list());
            typeVo.setFriendNum(typeVo.getQqFriends() == null ? 0 : typeVo.getQqFriends().size());
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
            user = lambdaQuery.eq(QqUser::getUid, req.getUserId()).one();
        } else if (LoginTypeEnum.PHONE_CODE.getType() == req.getLoginType()) {
            user = lambdaQuery.eq(QqUser::getPhone, req.getPhone()).one();
        } else if (LoginTypeEnum.EMAI_CODE.getType() == req.getLoginType()) {
            user = lambdaQuery.eq(QqUser::getEmai, req.getEmai()).one();
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
        LambdaQueryChainWrapper<QqGroup> groupLambdaQuery = new LambdaQueryChainWrapper(qqFriendGroupMapper);
        LambdaQueryChainWrapper<QqUser> userLambdaQuery = new LambdaQueryChainWrapper(qqUserMapper);
        List<QqFriendSessionVo> vo = sessionMapper.getSessionList(userId);
        if (CollectionUtil.isEmpty(vo)) {
            return new ArrayList<>();
        }
        vo.forEach(item -> {
            //查询好友信息
            item.setFriend(friendsLambdaQuery.eq(QqFriends::getUserId, userId).eq(QqFriends::getFriendUserId, item.getFriendUserId()).one());

            //查询好友具体信息
            if (FriendTypeEnum.FRIEND.getCode() == item.getSessionType()) {
                item.setFriendUser(userLambdaQuery.eq(QqUser::getUid, item.getFriendUserId()).one());
            } else {
                item.setGroup(groupLambdaQuery.eq(QqGroup::getGid, item.getFriendUserId()).one());
            }
            //查询消息记录
            item.setMessageList(chattingRecordsMapper.getChattingRecords(null, item.getSid(), MESSAGE_LIMIT));
            if (CollectionUtil.isNotEmpty(item.getMessageList())) {
                Collections.reverse(item.getMessageList());
            }

        });
        return null;
    }

    /**
     * 添加好友
     *
     * @param req
     * @return
     */
    public Result addFriend(AddFriendReq req) {
        //1.校验是否已经是好友
        LambdaQueryChainWrapper<QqFriends> qqFriendsLambda = ChainWrappers.lambdaQueryChain(QqFriends.class);
        QqFriends one = qqFriendsLambda.eq(QqFriends::getUserId, req.getUserId())
                .eq(QqFriends::getFriendUserId, req.getFriendUserId())
                .eq(QqFriends::getFriendType, FriendTypeEnum.FRIEND.getCode()).one();
        if (one != null) {
            throw new RuntimeException("已经是好友");
        }

        //2.检验用户是否正常
        LambdaQueryChainWrapper<QqUser> qqUserLambda = ChainWrappers.lambdaQueryChain(QqUser.class);
        QqUser user = qqUserLambda.eq(QqUser::getUid, req.getFriendUserId()).one();
        if (user == null || user.getUserStatus() != 1) {
            throw new RuntimeException("用户状态异常");
        }

        QqUser friend = qqUserLambda.eq(QqUser::getUid, req.getFriendUserId()).one();
        if (friend == null || friend.getUserStatus() != 1) {
            throw new RuntimeException("用户状态异常!");
        }
        QqAddFriendMessage message = sendAddFriendMessageBefore(req);
        JSONObject msgJson = JSON.parseObject(JSON.toJSONString(message, SerializerFeature.WriteMapNullValue));
        msgJson.put("userNickName", user.getUserNickName());
        //3.发送添加好友消息
        QqFriendChattingRecords record = new QqFriendChattingRecords();
        record.setToId(req.getFriendUserId());
        record.setFromId(req.getUserId());
        record.setChattingText(msgJson.toString(SerializerFeature.WriteMapNullValue));
        record.setMessageType(MessageTypeEnum.TEXT_MSG.getType());
        sendService.sendMsg(record, BusinessTypeEnum.ADD_FRIEND);
        return new Result(true, "发送成功", "200", null);
    }

    /**
     * 发送添加好友信息之前
     */
    private QqAddFriendMessage sendAddFriendMessageBefore(AddFriendReq req) {
        QqAddFriendMessage message = new QqAddFriendMessage();
        BeanUtil.copyProperties(req, message);
        message.setCreateTime(new Date());
        message.setUpdateTime(message.getCreateTime());
        message.setFromId(req.getUserId());
        message.setIsRead(0);
        message.setUserId(req.getFriendUserId());
        //新增群组
        if (req.getTypeId() == 0) {
            QqFriendsType type = addNewType(req.getUserId(), req.getTypeName());
            message.setTypeId(type.getTypeId());
        }
        addFriendMessageMapper.insert(message);
        return message;
    }

    private QqFriendsType addNewType(Integer userId, String typeName) {
        QqFriendsType type = new QqFriendsType();
        type.setTypeName(typeName);
        type.setUserId(userId);
        type.setCreateTime(new Date());
        type.setUpdateTime(type.getCreateTime());
        LambdaQueryChainWrapper<QqFriendsType> typeLambda = ChainWrappers.lambdaQueryChain(QqFriendsType.class);
        QqFriendsType qqFriendsType = typeLambda.eq(QqFriendsType::getUserId, userId).orderByDesc(QqFriendsType::getTypeOrder).last("limit 1 ").one();
        type.setTypeOrder(qqFriendsType.getTypeOrder() + 1);
        friendsTypeMapper.insert(type);
        return type;
    }


    /**
     * 处理添加好友请求
     *
     * @param req
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result dealWithAddFriend(DealWithAddFriendReq req) {
        LambdaQueryChainWrapper<QqAddFriendMessage> wrapper = ChainWrappers.lambdaQueryChain(QqAddFriendMessage.class);
        QqAddFriendMessage message = wrapper.eq(QqAddFriendMessage::getMessageId, req.getMessageId()).one();
        if (message.getDealWith() != 0) {
            return new Result(true, "已经处理", "200");
        }
        message.setDealWith(req.getDealWith());
        message.setUpdateTime(new Date());
        LambdaUpdateChainWrapper<QqAddFriendMessage> messageWrapper = ChainWrappers.lambdaUpdateChain(QqAddFriendMessage.class);
        messageWrapper.eq(QqAddFriendMessage::getMessageId, message.getMessageId()).set(QqAddFriendMessage::getDealWith, req.getDealWith()).set(QqAddFriendMessage::getIsRead, 1).set(QqAddFriendMessage::getUpdateTime, new Date());
        addFriendMessageMapper.update(messageWrapper);
        //新增群组
        if (req.getTypeId() == 0) {
            QqFriendsType type = addNewType(req.getUserId(), req.getTypeName());
            req.setTypeId(type.getTypeId());
        }
        if (2 == req.getDealWith()) {
            //同意添加好友
            LambdaQueryChainWrapper<QqFriends> qqFriendsLambda = ChainWrappers.lambdaQueryChain(QqFriends.class);
            QqFriends friend = qqFriendsLambda.eq(QqFriends::getUserId, message.getUserId()).eq(QqFriends::getFriendUserId, message.getFromId()).eq(QqFriends::getFriendType, FriendTypeEnum.FRIEND.getCode()).one();
            //不是好友才处理
            if (friend == null) {
                addFriendSave(req.getUserId(), message.getFromId(), req.getTypeId());
            }
            friend = qqFriendsLambda.eq(QqFriends::getUserId, message.getFromId()).eq(QqFriends::getFriendUserId, message.getUserId()).eq(QqFriends::getFriendType, FriendTypeEnum.FRIEND.getCode()).one();
            if (friend == null) {
                addFriendSave(message.getFromId(), req.getUserId(), message.getTypeId());
                QqFriendChattingRecords record = new QqFriendChattingRecords();
                record.setFromId(message.getFromId());
                record.setToId(message.getUserId());
                record.setChattingText(message.getMessage());
                sendService.sendMsg(record, BusinessTypeEnum.FRIEND_MESSAGE);
            }

        }
        return new Result(true, "添加成功", "200");
    }

    /**
     * 添加好友
     *
     * @param userId
     * @param friendUserId
     * @param typeId
     */
    private void addFriendSave(Integer userId, Integer friendUserId, Integer typeId) {
        QqFriends qqFriends = new QqFriends();
        qqFriends.setUserId(userId);
        qqFriends.setFriendUserId(friendUserId);
        qqFriends.setFriendType(FriendTypeEnum.FRIEND.getCode());
        qqFriends.setUpdateTime(new Date());
        qqFriends.setCreateTime(qqFriends.getUpdateTime());
        qqFriends.setTypeId(typeId);
        qqFriendsMapper.insert(qqFriends);
    }
}
