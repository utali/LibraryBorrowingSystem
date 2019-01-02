package com.yangch.dao;

import com.yangch.bean.User;

public interface LoginMapper {

    Integer userCount(User input);

    User login(User input);

    String getUserRegisterName(User input);

    String getUserRegisterID(User input);

    Integer register(User input);
}
