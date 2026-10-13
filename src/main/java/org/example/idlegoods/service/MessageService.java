package org.example.idlegoods.service;

import org.example.idlegoods.pojo.Message;
import java.util.List;

public interface MessageService {
//    // 发送消息
//    void sendMessage(Message message);
//
//    // 获取聊天记录
//    List<Message> getChatMessageList(Integer goodsId, Integer userId, Integer toUserId);
//
//    // 标为已读
//    void markMessageRead(Integer goodsId, Integer userId, Integer toUserId);
        void sendMessage(Message message);
        List<Message> getChatMessageList(Integer goodsId, Integer userId, Integer toUserId);
        void markMessageRead(Integer goodsId, Integer userId, Integer toUserId);

}