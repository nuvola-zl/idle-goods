package org.example.idlegoods.service;

import org.example.idlegoods.pojo.Goods;

import java.util.Map;

public interface MyGoodsService {

    //查询自己发布的作品
    Map<String, Object> listMyGoods(Integer pageNum, int i, Integer userId);

    //删除自己发布的商品
    void myGoodsDelete(Integer id,Integer userId);

    //通过id查询自己的商品
    Goods findById(Integer id);

    //修改商品信息
    void updateMyGoods(Goods goods,Integer userId);
}
