package org.example.idlegoods.controller;

import org.example.idlegoods.pojo.Goods;
import org.example.idlegoods.pojo.Result;
import org.example.idlegoods.service.MyGoodsService;
import org.example.idlegoods.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/myGoods")
public class MyGoodsController {

    @Autowired
    private MyGoodsService myGoodsService;
    //查询自己发布的作品
    @GetMapping("/myGoodsCheck")
    public Result myGoods(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestHeader("token") String token  // 从token拿当前用户ID
    ) {
        // 获取当前登录用户ID
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");

        // 固定一页10条
        Map<String, Object> map = myGoodsService.listMyGoods(pageNum, 10, userId);
        return Result.success(map);
    }

    //删除自己发布的商品，先查询自己发布的商品列表，才能删除
    //传入商品的id
    @DeleteMapping("/myGoodsCheck/delete/{id}")
    public Result myGoodsDelete( @RequestHeader("token") String token,  @PathVariable Integer id    )
    {
        // 获取当前登录用户ID
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");
        myGoodsService.myGoodsDelete(id ,userId);
        return Result.success("删除成功");
    }
    //修改某一个自己发布的商品
    //1.先显示自己的商品信息
    @GetMapping("/myGoodsCheck/show/{id}")
    public Result<Goods> showOneMyGood(@PathVariable Integer id )
    {
        Goods myGood=myGoodsService.findById(id);
        return Result.success(myGood);
    }
    //2.修改相关信息
    @PutMapping("/myGoodsCheck/update") // 去掉了路径里的 {id}
    public Result updateMyGoods(
            @RequestBody Goods goods,
            @RequestHeader("token") String token // 加了token，用于校验用户
    ) {
        // 获取当前登录用户ID
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");

        // 把userId传给service，确保只能改自己的
        myGoodsService.updateMyGoods(goods, userId);
        return Result.success("修改成功");
    }

}
