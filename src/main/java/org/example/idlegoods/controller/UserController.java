package org.example.idlegoods.controller;

import org.example.idlegoods.pojo.Goods;
import org.example.idlegoods.pojo.Result;
import org.example.idlegoods.pojo.User;
import org.example.idlegoods.service.UserService;
import org.example.idlegoods.utils.AliOssUtil;
import org.example.idlegoods.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    //展示用户信息
    @PostMapping("/info")
    public Result<User> showMsg(@RequestHeader("token") String token)
    {
        // 解析token拿到用户ID
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");

        //根据id查找用户信息
        User user= userService.findUserById(userId);

        return Result.success(user);

    }

    //1.修改并保存用户相关信息
    @PutMapping("/update")
    public Result update(@RequestBody User user, @RequestHeader("token") String token) {
        // 拿到当前登录用户ID
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");
        user.setId(userId); // 确保只能改自己的信息

        // 执行修改
        userService.update(user);

        return Result.success("修改成功");
    }

    // 2. 上传头像到阿里云
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(MultipartFile file) throws Exception {
        String avatarUrl = AliOssUtil.upload(file);
        return Result.success(avatarUrl);
    }

    // 1. 发布二手商品（主界面点【发布商品】调用）
    @PostMapping("/goodsAdd")
    public Result addGoods(@RequestBody Goods goods, @RequestHeader("token") String token) {
        // 获取当前登录用户ID
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");

        // 商品绑定当前登录用户
        goods.setUserId(userId);
        // 存入数据库
        userService.addGoods(goods);
        return Result.success("商品发布成功！");
    }

    @GetMapping("/categories")
    public Result getGoodsCategories() {
        List<String> categories = Arrays.asList(
                "衣服",
                "鞋子",
                "手机",
                "电脑",
                "美妆",
                "装饰",
                "建材",
                "图书",
                "其他"
        );
        return Result.success(categories);
    }

    // 2. 商品图片上传（阿里云OSS）
    @PostMapping("/goodsImage")
    public Result uploadGoodsImage(MultipartFile file) throws Exception {
        String imageUrl = AliOssUtil.upload(file);
        return Result.success(imageUrl);
    }

    //商品分页查询
    @GetMapping("/goodsPage")
    public Result goodsPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        Map<String, Object> map = userService.listGoodsByPage(pageNum, pageSize);
        return Result.success(map);
    }


    // ====================== 根据ID查询商品详情 ======================
    @GetMapping("/goods/{id}")
    public Result getGoodsById(@PathVariable Integer id) {
        Goods goods = userService.getGoodsById(id);
        return Result.success(goods);
    }

    //商品搜索查询
    @GetMapping("/goodsSearch")
    public Result searchGoods(
            @RequestParam(required = false) String category,     // 分类
            @RequestParam(required = false) String priceRange,   // 价格区间
            @RequestParam(required = false) String keyword,      // 关键词
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Map<String, Object> map = userService.searchGoods(category, priceRange, keyword, pageNum, pageSize);
        return Result.success(map);
    }

    //商品价格搜索下拉框
    @GetMapping("/priceRanges")
    public Result getPriceRanges() {
        List<String> ranges = Arrays.asList("0-50", "50-100", "100-200", "200+");
        return Result.success(ranges);
    }
    // 模拟支付 - 立即购买
    @PostMapping("/buy/{goodsId}")
    public Result buyGoods(
            @PathVariable Integer goodsId,  // 商品ID
            @RequestHeader("token") String token
    ) {
        // 获取当前登录用户ID
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("userId");

        // 执行购买（修改状态为0：已售）
        userService.buyGoods(goodsId, userId);
        return Result.success("购买成功！");
    }











}
