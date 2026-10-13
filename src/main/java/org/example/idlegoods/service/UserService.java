package org.example.idlegoods.service;

import org.apache.ibatis.annotations.Param;
import org.example.idlegoods.pojo.Goods;
import org.example.idlegoods.pojo.User;

import java.util.Map;

public interface UserService {

    //查看用户相关信息
    User findUserById( Integer userId);

    //修改用户相关信息
    void update(User user);

    //添加商品
    void addGoods(Goods goods);

    //商品分页查询
    Map<String, Object> listGoodsByPage(Integer pageNum, Integer pageSize);

    //查看商品详情
    Goods getGoodsById(Integer id);

    //搜索相关商品
    Map<String, Object> searchGoods(String category, String priceRange, String keyword, Integer pageNum, Integer pageSize);

    // 购买商品
    void buyGoods(Integer goodsId, Integer userId);

}
