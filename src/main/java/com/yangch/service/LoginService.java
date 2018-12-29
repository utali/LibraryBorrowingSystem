package com.yangch.service;

import com.yangch.bean.User;

public interface LoginService {

    Integer userCount(User input);

    User login(User input);
}
