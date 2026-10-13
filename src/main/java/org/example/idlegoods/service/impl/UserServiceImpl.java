package org.example.idlegoods.service.impl;

import org.example.idlegoods.mapper.UserMapper;
import org.example.idlegoods.pojo.Goods;
import org.example.idlegoods.pojo.User;
import org.example.idlegoods.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override

    //查询用户相关信息
    public User findUserById(Integer userId) {
        User user= userMapper.findUserById(userId);

        return user;
    }

    //更新用户信息
    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    //添加商品
    @Override
    public void addGoods(Goods goods) {

        // 默认有货
        goods.setStatus(1);
        userMapper.addGoods(goods);

    }

    @Override
    public Map<String, Object> listGoodsByPage(Integer pageNum, Integer pageSize) {
        // 计算从第几条开始查询
        int start = (pageNum - 1) * pageSize;

        // 1. 查询当前页商品列表
        List<Goods> goodsList = userMapper.listGoodsByPage(start, pageSize);

        // 2. 查询总条数
        Long total = userMapper.countGoods();

        // 3. 封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("goodsList", goodsList);
        map.put("total", total);

        return map;
    }



    @Override
    public Goods getGoodsById(Integer id) {
        return userMapper.getGoodsById(id);
    }

    @Override
    public Map<String, Object> searchGoods(String category, String priceRange, String keyword, Integer pageNum, Integer pageSize) {
        int start = (pageNum - 1) * pageSize;

        // 价格区间处理
        Double minPrice = null;
        Double maxPrice = null;
        if (priceRange != null && !priceRange.isEmpty()) {
            switch (priceRange) {
                case "0-50":
                    minPrice = 0.0;
                    maxPrice = 50.0;
                    break;
                case "50-100":
                    minPrice = 50.0;
                    maxPrice = 100.0;
                    break;
                case "100-200":
                    minPrice = 100.0;
                    maxPrice = 200.0;
                    break;
                case "200+":
                    minPrice = 200.0;
                    maxPrice = 9999.0;
                    break;
            }
        }

        List<Goods> goodsList = userMapper.searchGoods(
                category, minPrice, maxPrice, keyword, start, pageSize
        );

        Long total = userMapper.countSearchGoods(
                category, minPrice, maxPrice, keyword
        );

        Map<String, Object> map = new HashMap<>();
        map.put("goodsList", goodsList);
        map.put("total", total);
        return map;
    }

    @Override
    public void buyGoods(Integer goodsId, Integer userId) {
        // 1. 先查商品信息
        Goods goods = userMapper.getGoodsById(goodsId);

        // 2. 判断商品是否存在
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }

        // 3. 判断是否已售
        if(goods.getStatus() == 0) {
            throw new RuntimeException("商品已售出！");
        }

        // 4. 不能自己买自己的商品
        if (goods.getUserId().equals(userId)) {
            throw new RuntimeException("不能购买自己发布的商品！");
        }

        // 5. 模拟支付成功 → 修改状态为 0（已售）
        userMapper.updateGoodsStatus(goodsId, 0);
    }


}
