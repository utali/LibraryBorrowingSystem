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
    <meta charset="UTF-8">
    <title>welcome</title>

    <script type="text/javascript" src="js/jquery-3.3.1.js"></script>


    <style>
        body{
            overflow: hidden;
            margin: 0;
        }
        .imgDiv{
            width: 100%;
            height: 100%;
            position: absolute;
            background-color: #CCCCCC;
        }
        .imgClass{
            display: none;
        }
    </style>
    <script>
        var imgLast = 0;
        $(function () {
            $('#img'+imgLast).fadeIn(1000);
            setInterval(function () {
                var imgLength = $('.imgDiv').children().length;
                var imgNext = Math.floor(Math.random()*imgLength);
                $('#img'+imgLast).fadeOut(1000);
                $('#img'+imgNext).fadeIn(2500);
                imgLast = imgNext;
            },30000)
        })
    </script>
</head>
<body>
    <div class="imgDiv">
        <img id="img0" src="image/0.jpg" class="imgClass">
        <img id="img1" src="image/1.jpg" class="imgClass">
        <img id="img2" src="image/2.jpg" class="imgClass">
        <img id="img3" src="image/3.jpg" class="imgClass">
        <img id="img4" src="image/4.jpg" class="imgClass">
        <img id="img5" src="image/5.jpg" class="imgClass">
    </div>
</body>
</html>