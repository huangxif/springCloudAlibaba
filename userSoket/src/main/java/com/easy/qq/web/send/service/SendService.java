package com.easy.qq.web.send.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.easy.qq.conmon.Result;
import com.easy.qq.conmon.enums.BusinessTypeEnum;
import com.easy.qq.conmon.enums.FriendTypeEnum;
import com.easy.qq.entity.QqFriendChattingRecords;
import com.easy.qq.entity.QqFriendSession;
import com.easy.qq.entity.QqFriendSessionChattingRelation;
import com.easy.qq.entity.QqFriends;
import com.easy.qq.mapper.QqAddFriendMessageMapper;
import com.easy.qq.mapper.QqFriendChattingRecordsMapper;
import com.easy.qq.mapper.QqFriendSessionChattingRelationMapper;
import com.easy.qq.mapper.QqFriendSessionMapper;
import com.easy.qq.web.send.vo.MessageVo;
import com.easy.qq.web.send.vo.SendMsgOkVo;
import com.easy.qq.web.send.vo.SendPersonMsgVo;
import com.easy.qq.web.user.vo.ChattingRecordsVo;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class SendService {
    @Resource
    private MySocketHandler socketHandler;
    @Resource
    private QqFriendSessionMapper sessionMapper;
    @Resource
    private QqFriendChattingRecordsMapper chattingRecordsMapper;
    @Resource
    private QqFriendSessionChattingRelationMapper chattingRelationMapper;
    @Resource
    private QqAddFriendMessageMapper businessMessageMapper;

    /**
     * 发送单人消息
     *
     * @param record
     */
    public Result<Void> sendMsg(QqFriendChattingRecords record, BusinessTypeEnum businessType) {
        if (!businessType.equals(BusinessTypeEnum.FRIEND_MESSAGE)) {
            return null;
        }
        ChattingRecordsVo vo = sendMessageBefore(record);
        MessageVo messageVo = new MessageVo<>();
        messageVo.setMsgId(vo.getCrid());
        messageVo.setSend(vo.getFromId());
        messageVo.setTo(vo.getToId());
        messageVo.setType(businessType.getCode());
        messageVo.setData(vo);
        return sendMsg(messageVo);
    }

    private Result<Void> sendMsg(MessageVo message) {
        //TODO 单机直接发送,分布式需要借助redis,MQ
        Map<String, Channel> channelMap = socketHandler.CONCURRENT_HASH_MAP.get(message.getTo());
        if (CollectionUtil.isEmpty(channelMap)) {
            return new Result(true, "发送离线成功", "000000");
        }
        String str = message.toString();
        channelMap.entrySet().stream().forEach(entry -> {
            entry.getValue().writeAndFlush(new TextWebSocketFrame(str));
        });
        return new Result(true, "发送成功", "000000");
    }

    /**
     * 发送消息
     *
     * @return
     */
    private ChattingRecordsVo sendMessageBefore(QqFriendChattingRecords record) {
        //TODO 黑名单校验:
        //1.双方建立会话
        //1.1 获取接受方会话
        QqFriendSession toSession = createQqFriendSession(record, record.getToId(), record.getFromId());
        //1.1 获取发送方会话
        QqFriendSession fromSession = createQqFriendSession(record, record.getFromId(), record.getToId());
        //2.存储消息
        record.setCreateDate(new Date());
        record.setCreateTime(record.getCreateDate().getTime());
        chattingRecordsMapper.insert(record);
        QqFriendSessionChattingRelation relation = createSessionChattingRelation(record, toSession.getSid(), fromSession.getSid());
        ChattingRecordsVo vo = new ChattingRecordsVo();
        BeanUtil.copyProperties(relation, vo);
        BeanUtil.copyProperties(record, vo);
        return vo;
    }

    /**
     * 消息关联到会话
     *
     * @param record
     * @param toSessionId
     * @param fromSession
     */
    private QqFriendSessionChattingRelation createSessionChattingRelation(QqFriendChattingRecords record, Integer toSessionId, Integer fromSession) {
        //消息关联到会话
        QqFriendSessionChattingRelation toRelation = new QqFriendSessionChattingRelation(null, record.getCid(), toSessionId, 1, 0);
        QqFriendSessionChattingRelation fromRelation = new QqFriendSessionChattingRelation(null, record.getCid(), fromSession, 1, 1);
        //更新最后一条消息标志
        int i = chattingRelationMapper.updateLastFlag(toSessionId, fromSession);
        chattingRelationMapper.insert(fromRelation);
        //更新关系
        return toRelation;

    }

    /**
     * 创建会话
     *
     * @param record
     * @param userId
     * @param friendUserId
     * @return
     */
    private QqFriendSession createQqFriendSession(QqFriendChattingRecords record, Integer userId, Integer friendUserId) {
        LambdaQueryChainWrapper<QqFriendSession> sessionMapperLambda = new LambdaQueryChainWrapper(sessionMapper);
        QqFriendSession friendSession = sessionMapperLambda.eq(QqFriendSession::getUserId, userId).eq(QqFriendSession::getFriendUserId, friendUserId).one();
        //不存在建立
        if (friendSession == null) {
            friendSession = new QqFriendSession(null, userId, friendUserId, record.getGroupId() == 0 ? FriendTypeEnum.FRIEND.getCode() : FriendTypeEnum.GROUP.getCode(), 1, new Date(), new Date());
            sessionMapper.insert(friendSession);
        } else if (friendSession.getSessionStatus() == 2) {
            //隐藏会话设置为显示
            friendSession.setSessionStatus(1);
            sessionMapper.updateById(friendSession);
        }
        return friendSession;
    }

    /**
     * 发送个人消息
     *
     * @param sendMsgVo
     * @return
     */
    public Result<Void> sendPersonMsg(SendPersonMsgVo sendMsgVo) {
        //1.检验是否好友
        LambdaQueryChainWrapper<QqFriends> qqFriendsLambda = ChainWrappers.lambdaQueryChain(QqFriends.class);
        QqFriends one = qqFriendsLambda.eq(QqFriends::getUserId, sendMsgVo.getFrom()).eq(QqFriends::getFriendUserId, sendMsgVo.getTo()).eq(QqFriends::getFriendType, FriendTypeEnum.FRIEND).one();
        if (one == null) {
            throw new RuntimeException("对方不是您的好友");
        }
        one = qqFriendsLambda.eq(QqFriends::getUserId, sendMsgVo.getTo()).eq(QqFriends::getFriendUserId, sendMsgVo.getFrom()).eq(QqFriends::getFriendType, FriendTypeEnum.FRIEND).one();
        if (one == null) {
            throw new RuntimeException("您不是对方的好友");
        }
        QqFriendChattingRecords record = new QqFriendChattingRecords();
        sendMsg(record, BusinessTypeEnum.FRIEND_MESSAGE);
        return new Result(true, "发送成功", "200");
    }

    /**
     * 消息已读回执
     *
     * @param vo
     * @return
     */
    public Result<Void> sendMsgOk(SendMsgOkVo vo) {
        int i = chattingRelationMapper.updateByIds(vo.getUserId(), vo.getIds());
        //TODO 多端登录，处理其他端消息为已读
        return new Result<>(true, "修改成功", "200");
    }
}
