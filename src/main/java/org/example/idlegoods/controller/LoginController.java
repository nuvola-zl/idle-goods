package org.example.idlegoods.controller;

import org.example.idlegoods.DTO.LoginUserDTO;
import org.example.idlegoods.pojo.Result;
import org.example.idlegoods.pojo.User;
import org.example.idlegoods.service.LoginService;
import org.example.idlegoods.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result Login(@RequestBody LoginUserDTO loginUserDTO)
    {
        System.out.println("=== 收到登录请求 ===");
        System.out.println("username=" + loginUserDTO.getUsername());
        System.out.println("password=" + loginUserDTO.getPassword());

        try {
            String username = loginUserDTO.getUsername();
            String password = loginUserDTO.getPassword();

            System.out.println("准备调用 loginService.findByUsername...");
            User user = loginService.findByUsername(username);
            System.out.println("查询返回的 user 对象: " + user);

            if (user == null) {
                System.out.println("用户不存在，返回错误");
                return Result.error("用户名不存在");
            }

            System.out.println("找到用户: id=" + user.getId() + ", username=" + user.getUsername());
            System.out.println("数据库密码: " + user.getPassword());
            System.out.println("输入密码: " + password);
            System.out.println("密码是否匹配: " + user.getPassword().equals(password));

            if (!user.getPassword().equals(password)) {
                return Result.error("密码错误");
            }

            // 生成 JWT
           Map<String, Object> claims = new HashMap<>();
           claims.put("userId", user.getId());
            String token = JwtUtil.generateToken(claims);

            //System.out.println("登录成功，生成token: " + token.substring(0, 20) + "...");

            //此处用于测试
           // String token = "test_token_12345";  // 临时用固定值

            System.out.println("登录成功，返回测试token");


            return Result.success(token);

        } catch (Exception e) {
            System.out.println("!!! 发生异常 !!!");
            e.printStackTrace();
            return Result.error("系统错误: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody LoginUserDTO loginUserDTO)
    {
        //先判断用户名是否存在
        String username=loginUserDTO.getUsername();
        User user=loginService.findByUsername(username);
        String password=loginUserDTO.getPassword();

        if(username.length()<3||password.length()<6)
        {
            return Result.error("用户名或密码格式不正确，请重新输入");
        }

        if(user!=null)
        {
            return Result.error("该用户名已存在，请换一个用户名,已有帐号，请登录");
        }


        loginService.addUser(username,password);

        return Result.success("注册成功，请登录");
    }

}
