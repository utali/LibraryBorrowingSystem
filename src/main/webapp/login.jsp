<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/17
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <title>Welcome To LibraryBorrowingSystem</title>
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
    <script type="text/javascript" src="js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="js/login.js"></script>

    <style type="text/css">
        body{
            overflow: hidden;
            margin: 0;
        }
        .imgDiv{
            width: 100%;
            height: 100%;
            position: absolute;
            background-color: #CCCCCC;
            z-index: -1;
        }
        .imgClass{
            display: none;
        }
        .imgCheckCode{
            width: 20%;
            height: 35px;
            padding-left: 15px;
            cursor: pointer;
        }
        .loginAlert{
            float: left;
            padding: 5px 25px;
            color: #e69303;
            background-color: #2f1313;
            border-radius: 4px;
        }
    </style>

    <script type="text/javascript">
        // 登录/注册页面定时更换背景图--定时器
        var imgLast = 0;
        $(function () {
            $('#img'+imgLast).fadeIn(1000);
            setInterval(function () {
                var imgLength = $('.imgDiv').children().length;
                var imgNext = Math.floor(Math.random()*imgLength);
                $('#img'+imgLast).fadeOut(1000); //上一张背景图渐渐消失（1秒）
                $('#img'+imgNext).fadeIn(2500);  //下一张背景图渐渐出来（2.5秒）
                imgLast = imgNext;
            },30000) //30秒更换一次

        });
        
        $(function () {
            //监控--登录模态框打开
            $('#login').on('show.bs.modal',openLoginModal);
            //监控--登录模态框关闭
            $('#login').on('hide.bs.modal',closeLoginModal);
            //禁用空白处点击关闭
            $('#login').modal({
                backdrop: 'static',
                keyboard: false,//禁止键盘
                show:false
            });
            //监控--注册模态框打开
            $('#register').on('show.bs.modal',openRegisterModal);
            //监控--注册模态框关闭
            $('#register').on('hide.bs.modal',closeRegisterModal);
            //禁用空白处点击关闭
            $('#register').modal({
                backdrop: 'static',
                keyboard: false,//禁止键盘
                show:false
            });
        })
    </script>

</head>
<body>
    <%--登录页定时轮播图--%>
    <div class="imgDiv">
        <img id="img0" src="image/0.jpg" class="imgClass">
        <img id="img1" src="image/1.jpg" class="imgClass">
        <img id="img2" src="image/2.jpg" class="imgClass">
        <img id="img3" src="image/3.jpg" class="imgClass">
        <img id="img4" src="image/4.jpg" class="imgClass">
        <img id="img5" src="image/5.jpg" class="imgClass">
        <img id="img6" src="image/6.jpg" class="imgClass">
    </div>
    <%--登录页：登录/注册按钮--%>
    <div>
        <ul class="nav navbar-nav navbar-left">
            <li><a data-toggle="modal" data-target="#register" href=""><span class="glyphicon glyphicon-user" style="font-size: 25px"></span><span style="font-size: 25px"> 注册</span></a></li>
            <li><a data-toggle="modal" data-target="#login" href=""><span class="glyphicon glyphicon-log-in" style="font-size: 25px"></span><span style="font-size: 25px"> 登录</span></a></li>
        </ul>
    </div>

    <%--登录 模态框--%>
    <div class="modal fade" id="login" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">登录</h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-user"></span>
                        </span>
                        <input type="text" id="loginID" class="form-control" placeholder="请输入账号" style="width: 90%" onblur="loginIDBlur()" onfocus="loginIDFocus()">
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-lock"></span>
                        </span>
                        <input type="password" id="loginPwd" class="form-control" placeholder="请输入密码" style="width: 90%" onfocus="loginPwdFocus()">
                    </div>
                    <br>
                    <div>
                        <input type="text" id="loginCheckCode" class="form-control" placeholder="请输入验证码" style="width: 70%;float: left;" maxlength="4" onfocus="loginCheckCodeFocus()">
                        <img title="点击更换验证码" id="imgCheckCode" class="imgCheckCode" src="login/getLoginImgCheckCode" alt="验证码" onclick="changeLoginCheckCode()">
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <div class="loginAlert" style="display: none">
                        <strong><span id="loginMessage"></span></strong>
                    </div>
                    <div style="float: right">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" data-loading-text="Loading..." onclick="login(this)">登录</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--注册 模态框--%>
    <div class="modal fade" id="register" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">注册</h4>
                </div>
                <div class="modal-body">在这里添加一些文本</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary">提交更改</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>