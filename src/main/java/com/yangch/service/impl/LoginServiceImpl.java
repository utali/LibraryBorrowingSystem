package com.yangch.service.impl;

import com.yangch.bean.User;
import com.yangch.dao.LoginMapper;
import com.yangch.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public Integer userCount(User input) {
        return loginMapper.userCount(input);
    }

    @Override
    public User login(User input) {
        return loginMapper.login(input);
    }

    @Override
    public void updateLastLoginTime(User user) {
        loginMapper.updateLastLoginTime(user);
    }

    @Override
    public String getUserRegisterName(User input) {
        return loginMapper.getUserRegisterName(input);
    }

    @Override
    public String getUserRegisterID(User input) {
        return loginMapper.getUserRegisterID(input);
    }

    @Override
    public Integer register(User input) {
        return loginMapper.register(input);
    }
}
