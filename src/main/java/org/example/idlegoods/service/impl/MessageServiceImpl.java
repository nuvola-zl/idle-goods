package org.example.idlegoods.service.impl;

import org.example.idlegoods.mapper.MessageMapper;
import org.example.idlegoods.mapper.UserMapper;
import org.example.idlegoods.pojo.Message;
import org.example.idlegoods.service.MessageService;
import org.example.idlegoods.service.UserService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

//    @Resource
//    private MessageMapper messageMapper;
//
//    @Override
//    public void sendMessage(Message message) {
//        messageMapper.insertMessage(message);
//    }
//
//    @Override
//    public List<Message> getChatMessageList(Integer goodsId, Integer userId, Integer toUserId) {
//        return messageMapper.selectMessageList(goodsId, userId, toUserId);
//    }
//
//    @Override
//    public void markMessageRead(Integer goodsId, Integer userId, Integer toUserId) {
//        messageMapper.updateMessageRead(goodsId, userId, toUserId);
//    }

    @Resource
    private MessageMapper messageMapper;

    @Override
    public void sendMessage(Message message) {
        messageMapper.insertMessage(message);
    }

    // 【关键修复】查询双方聊天记录
    @Override
    public List<Message> getChatMessageList(Integer goodsId, Integer userId, Integer toUserId) {
        return messageMapper.selectChatMessages(goodsId, userId, toUserId);
    }

    @Override
    public void markMessageRead(Integer goodsId, Integer userId, Integer toUserId) {
        messageMapper.updateMessageRead(goodsId, toUserId, userId);
    }
}