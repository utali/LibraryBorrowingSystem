package com.yangch.controller;

import com.alibaba.fastjson.JSONObject;
import com.yangch.bean.User;
import com.yangch.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/hello")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/test",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String test(){
        JSONObject jsonObject = new JSONObject();
        List<User> user = testService.getUserList();
        jsonObject.put("user",user);
        return jsonObject.toString();
    }
}
