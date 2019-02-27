package com.yangch.bean;

import lombok.Data;

import java.util.Date;

@Data
public class User {

  //用户在网站的唯一标识
  private String userId;
  //用户的身份信息
  private String idCard;
  //用户输入的密码
  private String password;
  //用户重复输入的密码
  private String passwordAgain;
  //昵称
  private String name;
  //用户类型(0是借阅者，1是管理员)
  private String type;
  //账户余额
  private Double userOver;
  private String delFlag;
  //用户上次登录时间
  private Date lastLoginTime;
  private Date createTime;
  private String createUser;
  private Date updateTime;
  private String updateUser;
  //用户输入的验证码
  private String checkCode;

}
