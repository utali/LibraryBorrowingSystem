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
    <title>welcome</title>
</head>
<body>
    <h1>YC</h1>
    <input type="button" value="按钮" onclick="queryUser()">
    <a href="hello/test">lalala</a>
${lalala}
</body>
<%--<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>--%>
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script>
    function queryUser() {
        $.ajax({
            url:'hello/test',
            type:'post',
            data:'json',
            dataType:'json',
            success:function (data) {
                console.log(data)
                alert(data)
            }

        })
    }
</script>
</html>
