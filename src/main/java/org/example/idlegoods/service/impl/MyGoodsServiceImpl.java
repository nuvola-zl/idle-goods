package org.example.idlegoods.service.impl;

import org.example.idlegoods.mapper.MyGoodsMapper;
import org.example.idlegoods.pojo.Goods;
import org.example.idlegoods.service.MyGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyGoodsServiceImpl implements MyGoodsService {

    @Autowired
    private MyGoodsMapper myGoodsMapper;
    @Override
    public Map<String, Object> listMyGoods(Integer pageNum, int pageSize, Integer userId) {
        int start = (pageNum - 1) * pageSize;
        // 查询自己的商品列表
        List<Goods> goodsList = myGoodsMapper.listMyGoods(start, pageSize, userId);
        // 查询自己的商品总数
        Long total = myGoodsMapper.countMyGoods(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("goodsList", goodsList);
        map.put("total", total);
        return map;

    }

    @Override
    public void myGoodsDelete(Integer id,Integer userId) {

        myGoodsMapper.myGoodsDelete(id,userId);

    }

    @Override
    public Goods findById(Integer id) {
        Goods goods=myGoodsMapper.findById(id);
        return goods;
    }

    @Override
    public void updateMyGoods(Goods goods ,Integer userId) {
        goods.setUserId(userId);
        myGoodsMapper.updateMyGoods(goods);
    }
}
