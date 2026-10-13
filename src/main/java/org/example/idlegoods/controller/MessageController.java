package org.example.idlegoods.controller;

import org.example.idlegoods.pojo.Message;
import org.example.idlegoods.pojo.Result;
import org.example.idlegoods.service.MessageService;
import org.example.idlegoods.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    // 发送消息
    @PostMapping("/send")
    public Result sendMessage(
            @RequestBody Message message,
            @RequestHeader("token") String token
    ) {
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");

        message.setFromUserId(userId);
        message.setIsRead(0);

        messageService.sendMessage(message);
        return Result.success("发送成功");
    }

    // 获取聊天记录【已修复】
    @GetMapping("/list")
    public Result<List<Message>> getMessageList(
            @RequestParam Integer goodsId,
            @RequestParam Integer toUserId,
            @RequestHeader("token") String token
    ) {
        Integer userId = (Integer) JwtUtil.parseToken(token).get("userId");
        List<Message> list = messageService.getChatMessageList(goodsId, userId, toUserId);
        return Result.success(list);
    }

    // 标为已读
    @GetMapping("/read")
    public Result markRead(
            @RequestParam Integer goodsId,
            @RequestParam Integer toUserId,
            @RequestHeader("token") String token
    ) {
        Integer userId = (Integer) JwtUtil.parseToken(token).get("userId");
        messageService.markMessageRead(goodsId, userId, toUserId);
        return Result.success("已读");
    }
}