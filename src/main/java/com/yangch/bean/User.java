package com.yangch.bean;

import lombok.Data;

import java.util.Date;

@Data
public class User {

  private String userId;
  private String idCard;
  private String password;
  private String name;
  private String type;
  private String delFlag;
  private Date createTime;
  private String createUser;
  private Date updateTime;
  private String updateUser;
  private String checkCode;

}
