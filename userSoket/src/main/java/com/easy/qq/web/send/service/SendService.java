package com.easy.qq.web.send.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.easy.qq.conmon.Result;
import com.easy.qq.entity.QqFriendChattingRecords;
import com.easy.qq.entity.QqFriendSession;
import com.easy.qq.entity.QqFriendSessionChattingRelation;
import com.easy.qq.mapper.QqFriendChattingRecordsMapper;
import com.easy.qq.mapper.QqFriendSessionChattingRelationMapper;
import com.easy.qq.mapper.QqFriendSessionMapper;
import com.easy.qq.web.send.vo.MessageVo;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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

    /**
     * 发送单人消息
     *
     * @param record
     */
    public Result<Void> sendMsg(QqFriendChattingRecords record) {
        sendMessageBefore(record);
        //TODO 单机直接发送,分布式需要借助redis,MQ
        Channel channel = socketHandler.CONCURRENT_HASH_MAP.get(record.getToId());
        if (channel == null) {
            return new Result(true, "发送离线成功", "000000", null);
        }
        channel.writeAndFlush(new TextWebSocketFrame(record.getChattingText()));
        return new Result(true, "发送成功", "000000", null);
    }


    /**
     * 发送消息
     *
     * @return
     */
    private void sendMessageBefore(QqFriendChattingRecords record) {
        //TODO 建立会话前校验是否可以发送消息：是否删除好友，黑名单，拒
        //1.双方建立会话
        //1.1 获取接受方会话
        QqFriendSession toSession = createQqFriendSession(record, record.getToId(), record.getFromId());
        //1.1 获取发送方会话
        QqFriendSession fromSession = createQqFriendSession(record, record.getFromId(), record.getToId());
        //2.存储消息
        record.setCreateDate(new Date());
        record.setCreateTime(record.getCreateDate().getTime());
        chattingRecordsMapper.insert(record);
        createSessionChattingRelation(record, toSession.getSid(), fromSession.getSid());
    }

    /**
     * 消息关联到会话
     *
     * @param record
     * @param toSessionId
     * @param fromSession
     */
    private void createSessionChattingRelation(QqFriendChattingRecords record, Integer toSessionId, Integer fromSession) {
        //消息关联到会话
        QqFriendSessionChattingRelation toRelation = new QqFriendSessionChattingRelation(null, record.getCid(), toSessionId, 1, 0);
        QqFriendSessionChattingRelation fromRelation = new QqFriendSessionChattingRelation(null, record.getCid(), fromSession, 1, 1);
        //更新最后一条消息标志
        int i = chattingRelationMapper.updateLastFlag(toSessionId, fromSession);
        //更新关系
        chattingRelationMapper.insert(toRelation);
        chattingRelationMapper.insert(fromRelation);
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
        QqFriendSession friendSession = sessionMapperLambda.eq(QqFriendSession::getUserId, userId).eq(QqFriendSession::getFriendUserId, friendUserId).getEntity();
        //不存在建立
        if (friendSession == null) {
            friendSession = new QqFriendSession(null, userId, friendUserId, record.getMessageGroup(), 1, new Date(), new Date());
            sessionMapper.insert(friendSession);
        } else if (friendSession.getSessionStatus() == 2) {
            //隐藏会话设置为显示
            friendSession.setSessionStatus(1);
            sessionMapper.updateById(friendSession);
        }
        return friendSession;
    }
}
