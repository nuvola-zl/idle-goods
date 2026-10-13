package org.example.idlegoods.mapper;

import org.apache.ibatis.annotations.*;
import org.example.idlegoods.pojo.Message;

import java.util.List;

@Mapper
public interface MessageMapper {

//    // 发送消息
//    @Insert("INSERT INTO message(goods_id, from_user_id, to_user_id, content, is_read) " +
//            "VALUES(#{goodsId}, #{fromUserId}, #{toUserId}, #{content}, #{isRead})")
//    void insertMessage(Message message);
//
//    // 查询两个人的聊天记录
//    @Select("SELECT m.*, u.username, u.avatar " +
//            "FROM message m " +
//            "LEFT JOIN user u ON m.from_user_id = u.id " +
//            "WHERE m.goods_id = #{goodsId} " +
//            "AND ((from_user_id = #{userId} AND to_user_id = #{toUserId}) " +
//            "OR (from_user_id = #{toUserId} AND to_user_id = #{userId})) " +
//            "ORDER BY m.create_time ASC")
//    List<Message> selectMessageList(Integer goodsId, Integer userId, Integer toUserId);
//
//    // 标为已读
//    @Update("UPDATE message SET is_read = 1 " +
//            "WHERE goods_id = #{goodsId} " +
//            "AND to_user_id = #{userId} " +
//            "AND from_user_id = #{toUserId}")
//    void updateMessageRead(Integer goodsId, Integer userId, Integer toUserId);


        // 发送消息
        @Insert("INSERT INTO message (goods_id, from_user_id, to_user_id, content, is_read, create_time) " +
                "VALUES (#{goodsId}, #{fromUserId}, #{toUserId}, #{content}, #{isRead}, NOW())")
        void insertMessage(Message message);

        // 查询双向聊天记录（核心修复）
        @Select("SELECT * FROM message " +
                "WHERE goods_id = #{goodsId} " +
                "AND ((from_user_id = #{userId} AND to_user_id = #{toUserId}) " +
                "OR (from_user_id = #{toUserId} AND to_user_id = #{userId})) " +
                "ORDER BY create_time ASC")
        List<Message> selectChatMessages(
                @Param("goodsId") Integer goodsId,
                @Param("userId") Integer userId,
                @Param("toUserId") Integer toUserId
        );

        // 标为已读
        @Update("UPDATE message SET is_read = 1 " +
                "WHERE goods_id = #{goodsId} " +
                "AND from_user_id = #{fromUserId} " +
                "AND to_user_id = #{toUserId}")
        void updateMessageRead(
                @Param("goodsId") Integer goodsId,
                @Param("fromUserId") Integer fromUserId,
                @Param("toUserId") Integer toUserId
        );

}