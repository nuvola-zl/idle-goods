package org.example.idlegoods.DTO;

import lombok.Data;

@Data
public class LoginUserDTO {

    // 接收用户名
    private String username;

    // 接收密码
    private String password;
}