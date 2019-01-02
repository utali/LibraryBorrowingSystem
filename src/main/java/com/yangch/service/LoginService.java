package com.yangch.service;

import com.yangch.bean.User;

public interface LoginService {

    Integer userCount(User input);

    User login(User input);

    String getUserRegisterName(User input);

    String getUserRegisterID(User input);

    Integer register(User input);
}
