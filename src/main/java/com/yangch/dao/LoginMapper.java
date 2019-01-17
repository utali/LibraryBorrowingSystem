package com.yangch.dao;

import com.yangch.bean.User;

public interface LoginMapper {

    Integer userCount(User input);

    User login(User input);

    void updateLastLoginTime(User user);

    String getUserRegisterName(User input);

    String getUserRegisterID(User input);

    Integer register(User input);

}
