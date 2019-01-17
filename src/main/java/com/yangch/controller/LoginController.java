package com.yangch.controller;

import com.alibaba.fastjson.JSONObject;
import com.yangch.bean.User;
import com.yangch.service.LoginService;
import com.yangch.util.ImageCheckCodeUtil;
import com.yangch.util.RandomUtil;
import com.yangch.util.UUIDUtil;
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
     * 生成登录图片验证码
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
     * 生成注册图片验证码
     */
    @RequestMapping(value = "/getRegisterImgCheckCode", method = RequestMethod.GET)
    public void getRegisterImgCheckCode(HttpServletResponse response, HttpSession session) throws Exception {
        log.info("生成图片验证码");
        String randomStr = "123456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        // 取得一个4位随机字母数字字符串
        String s = RandomUtil.random(4,randomStr);
        // 保存入session,用于与用户的输入进行比较.
        // 注意比较完之后清除session.
        session.setAttribute("registerCheckCode", s);
        response.setContentType("images/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String checkCode = (String) session.getAttribute("registerCheckCode");
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
        //更新登陆时间
        loginService.updateLastLoginTime(user);
        log.info("登录成功");
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
            log.info("用户未登录");
            return "redirect:/";
        }
        return "main";
    }

    /**
     * 注册模态框昵称重复校验
     */
    @RequestMapping(value = "/getUserRegisterName", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String getUserRegisterName(User input){
        log.info("注册校验昵称");
        JSONObject jsonObject = new JSONObject();
        String userId = loginService.getUserRegisterName(input);
        if(userId != null){
            log.info("昵称重复");
            jsonObject.put("status",1);
            jsonObject.put("message","昵称重复，请重新填写");
            return jsonObject.toString();
        }
        log.info("昵称通过");
        jsonObject.put("status",0);
        return jsonObject.toString();
    }

    /**
     * 注册模态框账号重复校验
     */
    @RequestMapping(value = "/getUserRegisterID", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String getUserRegisterID(User input){
        log.info("注册校验账号");
        JSONObject jsonObject = new JSONObject();
        String userId = loginService.getUserRegisterID(input);
        if(userId != null){
            log.info("账号已存在");
            jsonObject.put("status",1);
            jsonObject.put("message","该账号已存在");
            return jsonObject.toString();
        }
        log.info("账号校验通过");
        jsonObject.put("status",0);
        return jsonObject.toString();
    }

    /**
     * 注册并登陆
     */
    @RequestMapping(value = "/register",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String register(User input,HttpSession session){
        log.info("注册开始");
        JSONObject jsonObject = new JSONObject();
        String registerCheckCode = (String) session.getAttribute("registerCheckCode");
        if(registerCheckCode == null || !registerCheckCode.equalsIgnoreCase(input.getCheckCode())){
            log.info("验证码错误");
            jsonObject.put("registerStatus",1);
            jsonObject.put("message","验证码错误");
            session.removeAttribute("registerCheckCode");
            return jsonObject.toString();
        }
        if(input.getPassword().length() < 6){
            log.info("密码少于6位");
            jsonObject.put("registerStatus",2);
            jsonObject.put("message","密码少于6位，请重新输入");
            session.removeAttribute("registerCheckCode");
            return jsonObject.toString();
        }
        if(!input.getPassword().equals(input.getPasswordAgain())){
            log.info("两次输入的密码不一致");
            jsonObject.put("registerStatus",3);
            jsonObject.put("message","两次输入的密码不一致");
            session.removeAttribute("registerCheckCode");
            return jsonObject.toString();
        }
        String userId = loginService.getUserRegisterName(input);
        if(userId != null){
            log.info("昵称重复");
            jsonObject.put("registerStatus",4);
            jsonObject.put("message","昵称重复，请重新填写");
            return jsonObject.toString();
        }
        String userId2 = loginService.getUserRegisterID(input);
        if(userId2 != null){
            log.info("账号已存在");
            jsonObject.put("registerStatus",5);
            jsonObject.put("message","该账号已存在");
            return jsonObject.toString();
        }
        input.setUserId(UUIDUtil.getUuid());
        Integer flag = loginService.register(input);
        if(flag != 1){
            log.info("注册失败");
            jsonObject.put("registerStatus",6);
            jsonObject.put("message","注册失败，请稍后重试");
            return jsonObject.toString();
        }
        User user = loginService.login(input);
        session.setAttribute("user",user);
        log.info("注册成功");
        jsonObject.put("registerStatus",0);
        return jsonObject.toString();

    }
}
