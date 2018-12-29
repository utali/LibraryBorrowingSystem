package com.yangch.controller;

import com.alibaba.fastjson.JSONObject;
import com.yangch.bean.User;
import com.yangch.service.LoginService;
import com.yangch.util.ImageCheckCodeUtil;
import com.yangch.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 生成图片验证码
     */
    @RequestMapping(value = "/getLoginImgCheckCode", method = RequestMethod.GET)
    public void getImgCheckCode(HttpServletResponse response, HttpSession session) throws Exception {
        log.info("生成图片验证码");
        String randomStr = "123456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        // 取得一个4位随机字母数字字符串
        String s = RandomUtil.random(4,randomStr);
        // 保存入session,用于与用户的输入进行比较.
        // 注意比较完之后清除session.
        session.setAttribute("loginCheckCode", s);
        response.setContentType("images/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String checkCode = (String) session.getAttribute("loginCheckCode");
        String sessionId = session.getId();
        log.info("session:"+session.toString());
        log.info("sessionId:"+sessionId);
        log.info("随机验证码:"+s+",session记录：" + checkCode);
        ServletOutputStream out = response.getOutputStream();
        BufferedImage image = ImageCheckCodeUtil.getImage(s);
        // 输出图象到页面
        ImageIO.write((BufferedImage) image, "JPEG", out);
        out.close();
    }

    /**
     * 登录校验
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(User input, HttpSession session){
        log.info("开始登录校验");
        JSONObject jsonObject = new JSONObject();
        String checkCode = (String) session.getAttribute("loginCheckCode");
        if(checkCode == null || !checkCode.equalsIgnoreCase(input.getCheckCode())){
            log.info("验证码错误");
            jsonObject.put("loginStatus","1");
            jsonObject.put("message","验证码错误");
            session.removeAttribute("loginCheckCode");
            return jsonObject.toString();
        }
        Integer count = loginService.userCount(input);
        if(count != 1){
            log.info("账号或密码错误");
            jsonObject.put("loginStatus","2");
            jsonObject.put("message","请输入正确的账号或密码");
            session.removeAttribute("loginCheckCode");
            return jsonObject.toString();
        }
        User user = loginService.login(input);
        if(user == null){
            log.info("用户不存在");
            jsonObject.put("loginStatus","3");
            jsonObject.put("message","该用户不存在");
            session.removeAttribute("loginCheckCode");
            return jsonObject.toString();
        }
        log.info("登录校验结束");
        session.setAttribute("user",user);
        jsonObject.put("loginStatus","0");
        return jsonObject.toString();
    }

    /**
     * 登录成功跳转页面
     */
    @RequestMapping(value = "/loginSuccess", method = {RequestMethod.POST,RequestMethod.GET})
    public String loginSuccess(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        return "main";
    }
}
