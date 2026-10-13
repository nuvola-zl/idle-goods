package org.example.idlegoods.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Goods {
    private Integer id;
    private String name;        // 商品名
    private Double price;      // 价格
    private String description; // 描述
    private String image;      // 图片地址
    private String category;   // 分类
    private Integer status;    //商品状态，1：有货，0：没货
    private Integer userId;    // 发布人ID
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String username; // 加这个字段！用于接收查询出来的用户名

    private String avatar; // 用户头像:用于打开详情，商品所属用户头像
}