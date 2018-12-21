package com.yangch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangch.bean.User;
import com.yangch.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/hello")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/test",method = {RequestMethod.POST,RequestMethod.GET})
    public String test(HttpServletRequest request) throws JsonProcessingException {
        System.out.println(1111111);
        List<User> user = testService.getUserList();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(2222222);
        String jsonlist = mapper.writeValueAsString(user);
        request.setAttribute("lalala",user.get(0).getName());
        return "helloooo";
    }
}
