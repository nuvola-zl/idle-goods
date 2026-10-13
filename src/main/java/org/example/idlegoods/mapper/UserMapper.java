package org.example.idlegoods.mapper;

import org.apache.ibatis.annotations.*;
import org.example.idlegoods.pojo.Goods;
import org.example.idlegoods.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {

    //查看用户相关信息
    @Select("select * from user where id=#{id}")
    User findUserById(Integer userId);

    //修改用户相关信息
    @Update("update user set nickname=#{nickname}, avatar=#{avatar},update_time=now() where id=#{id}")
    void update(User user);

    //添加商品
    @Insert("insert into goods(name, price, description, image, category, status, user_id, create_time, update_time) " +
            "values(#{name}, #{price}, #{description}, #{image}, #{category}, #{status}, #{userId}, now(), now())")
    void addGoods(Goods goods);

    // 商品分页查询（连表查询用户名）
    @Select("SELECT g.*, u.username " +
            "FROM goods g " +
            "LEFT JOIN user u ON g.user_id = u.id " +
            "ORDER BY g.create_time DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<Goods> listGoodsByPage(Integer start, Integer pageSize);

    // 查询商品总数量
    @Select("select count(*) from goods")
    Long countGoods();

    //查看商品详情
    @Select("SELECT g.*, u.username, u.avatar " +
            "FROM goods g " +
            "LEFT JOIN user u ON g.user_id = u.id " +
            "WHERE g.id = #{id}")
    Goods getGoodsById(Integer id);

    // 搜索商品（修复版）
    @Select("<script> "
            + "SELECT g.*, u.username, u.avatar "
            + "FROM goods g "
            + "LEFT JOIN user u ON g.user_id = u.id "
            + "WHERE g.status = 1 "

            + " <if test='category != null and category != \"\"'> "
            + "   AND g.category = #{category} "
            + " </if> "

            + " <if test='minPrice != null'> "
            + "   AND g.price &gt;= #{minPrice} "
            + " </if> "

            + " <if test='maxPrice != null'> "
            + "   AND g.price &lt;= #{maxPrice} "
            + " </if> "

            + " <if test='keyword != null and keyword != \"\"'> "
            + "   AND (g.name LIKE CONCAT('%', #{keyword}, '%') "
            + "   OR g.description LIKE CONCAT('%', #{keyword}, '%')) "
            + " </if> "

            + "ORDER BY g.create_time DESC "
            + "LIMIT #{start}, #{pageSize} "
            + "</script>")
    List<Goods> searchGoods(
            String category,
            Double minPrice,
            Double maxPrice,
            String keyword,
            Integer start,
            Integer pageSize
    );

    // 统计搜索数量（修复版）
    @Select("<script> "
            + "SELECT COUNT(*) FROM goods "
            + "WHERE status = 1 "

            + " <if test='category != null and category != \"\"'> "
            + "   AND category = #{category} "
            + " </if> "

            + " <if test='minPrice != null'> "
            + "   AND price &gt;= #{minPrice} "
            + " </if> "

            + " <if test='maxPrice != null'> "
            + "   AND price &lt;= #{maxPrice} "
            + " </if> "

            + " <if test='keyword != null and keyword != \"\"'> "
            + "   AND (name LIKE CONCAT('%', #{keyword}, '%') "
            + "   OR description LIKE CONCAT('%', #{keyword}, '%')) "
            + " </if> "
            + "</script>")
    Long countSearchGoods(
            String category,
            Double minPrice,
            Double maxPrice,
            String keyword
    );

    // 修改商品状态（1=有货 → 0=无货）
    @Update("UPDATE goods SET status = #{status} WHERE id = #{goodsId}")
    void updateGoodsStatus(Integer goodsId, Integer status);

}