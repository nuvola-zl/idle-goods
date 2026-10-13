package org.example.idlegoods.pojo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Message {
        private Integer id;            // 消息ID
        private Integer goodsId;       // 商品ID
        private Integer fromUserId;    // 发送者ID
        private Integer toUserId;      // 接收者ID
        private String content;        // 消息内容
        private Integer isRead;        // 0未读 1已读 → 小红点靠这个字段！
        private LocalDateTime createTime; // 发送时间

        // ========== 下面这三个是给前端显示用的，数据库没有 ==========
        private String username;    // 发送者昵称
        private String avatar;      // 发送者头像

}
