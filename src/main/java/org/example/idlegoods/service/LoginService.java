package org.example.idlegoods.service;

import org.example.idlegoods.pojo.User;
public interface LoginService {
    //根据用户名查找用户
     User findByUsername(String username) ;


    //根据用户名密码注册用户
    void addUser(String username, String password);
}
