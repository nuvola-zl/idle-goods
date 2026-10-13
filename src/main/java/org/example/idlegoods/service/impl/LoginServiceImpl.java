package org.example.idlegoods.service.impl;

import org.example.idlegoods.mapper.LoginMapper;
import org.example.idlegoods.pojo.User;
import org.example.idlegoods.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;
    @Override
    public User findByUsername(String username) {
        System.out.println("=== LoginServiceImpl.findByUsername 开始 ===");
        System.out.println("传入参数 username: " + username);

        try {
            System.out.println("准备执行 SQL...");

            // 强制立即执行查询
            User user = loginMapper.findByUsername(username);

            System.out.println("SQL 执行完成，user=" + user);
            if (user != null) {
                System.out.println("user.id=" + user.getId());
                System.out.println("user.username=" + user.getUsername());
            }
            return user;
        } catch (Exception e) {
            System.out.println("!!! 异常: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void  addUser(String username, String password) {
        loginMapper.addUser(username,password);

    }
}
