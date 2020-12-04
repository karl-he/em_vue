package com.baizhi.service;

import com.baizhi.entity.User;

public interface UserService {

    void register(User user);

    //登陆
    User login(User user);
}
