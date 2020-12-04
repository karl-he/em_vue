package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @Override
    public void register(User user) {
        User username = userDao.findByUsername(user.getUsername());
        if(username == null){
            user.setStatus("已激活");
            user.setRegistertime(new Date());
            userDao.save(user);
        }else{
            throw new RuntimeException("用户名已存在");
        }

    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }
}
