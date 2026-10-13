package org.example.idlegoods.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.idlegoods.pojo.User;

@Mapper
public interface LoginMapper {

    // 必须加 @Param("username") 注解！
    //@Select("select id, username, password from user where username = #{username}")
    @Select("SELECT id, username, password, nickname, avatar FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // 这个也要加
    @Insert("insert into user(username, password, create_time, update_time) values(#{username}, #{password}, now(), now())")
    void addUser(@Param("username") String username, @Param("password") String password);
}