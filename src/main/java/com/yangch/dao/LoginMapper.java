package com.yangch.dao;

import com.yangch.bean.User;

public interface LoginMapper {

    Integer userCount(User input);

    User login(User input);
}
