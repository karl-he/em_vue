package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    //注册
    void save(User user);

    //根据用户名查询用户是否存在
    User findByUsername(String username);

    //登陆
    User login(User user);
}
