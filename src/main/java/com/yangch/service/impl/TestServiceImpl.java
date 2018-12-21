package com.yangch.service.impl;

import com.yangch.bean.User;
import com.yangch.dao.TestMapper;
import com.yangch.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    TestMapper testMapper;

    public List<User> getUserList() {
        return testMapper.getUserList();
    }
}
