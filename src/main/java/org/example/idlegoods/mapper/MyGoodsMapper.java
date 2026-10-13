package org.example.idlegoods.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.idlegoods.pojo.Goods;

import java.util.List;

@Mapper
public interface MyGoodsMapper {
    // 查询【当前用户】发布的所有商品（带分页 + 用户名）
    @Select("SELECT g.*, u.username " +
            "FROM goods g " +
            "LEFT JOIN user u ON g.user_id = u.id " +
            "WHERE g.user_id = #{userId} " +  // 关键：只查自己的
            "ORDER BY g.create_time DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<Goods> listMyGoods(Integer start, Integer pageSize, Integer userId);

    // 查询【我的商品】总条数
    @Select("select count(*) from goods where user_id = #{userId}")
    Long countMyGoods(Integer userId);


    //删除我的商品中的某一条数据
    @Delete("DELETE FROM goods WHERE id = #{id} AND user_id = #{userId}")
    void myGoodsDelete(Integer id, Integer userId);

    //查找商品信息
    @Select("SELECT * FROM goods WHERE id = #{id}")
    Goods findById(Integer id);

    //修改商品信息
    @Update("UPDATE goods SET name=#{name}, price=#{price}, description=#{description}, " +
            "category=#{category}, image=#{image}, update_time=now() " +
            "WHERE id=#{id} AND user_id=#{userId}")
    void updateMyGoods(Goods goods);
}
