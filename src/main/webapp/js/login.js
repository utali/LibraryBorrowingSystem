//登录模态框打开时--居中
function openLoginModal(){
    $('#login').each(function(i) {
        var $clone = $(this).clone().css('display','block').appendTo('body');
        var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2.5);
        top = top > 0 ? top : 0;
        $clone.remove();
        $(this).find('.modal-content').css("margin-top", top);
    });
}
//注册模态框打开时--居中
function openRegisterModal(){
    $('#register').each(function(i) {
        var $clone = $(this).clone().css('display','block').appendTo('body');
        var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2.5);
        top = top > 0 ? top : 0;
        $clone.remove();
        $(this).find('.modal-content').css("margin-top", top);
    });
}

//登录模态框关闭时--清空模态框中数据
function closeLoginModal() {
    $('#login input').val('');
    changeLoginCheckCode();
}
//注册模态框关闭时--清空模态框中数据
function closeRegisterModal() {
    $('#register input').val('');
}

// 更换登录模态框图片验证码
function changeLoginCheckCode() {
    document.getElementById("imgCheckCode").src = getContextPath() + "/login/getLoginImgCheckCode?codeVersion=" + new Date().valueOf();
}
function getContextPath() {
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/meun.jsp
    //获取主机地址，如： http://localhost:8083
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return (localhostPaht + projectName);
}

//登录模态框账号校验
function loginIDBlur() {
    var loginID = $('#loginID').val();
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if(typeof loginID == "undefined" || loginID == null || loginID == ""){
        $('#loginMessage').html("账号不能为空");
        $('.loginAlert').fadeIn(0);
        return 1;
    }
    if(reg.test(loginID) == false){
        $('#loginMessage').html("请输入正确的账号");
        $('.loginAlert').fadeIn(0);
        return 2;
    }
    return 0;
}
//获取焦点时隐藏错误信息
function loginIDFocus() {
    $('.loginAlert').fadeOut(0);
}
function loginPwdFocus() {
    $('.loginAlert').fadeOut(0);
    loginIDBlur();
}
function loginCheckCodeFocus() {
    $('.loginAlert').fadeOut(0);
    loginIDBlur();
}
//登录
function login(obj) {
    $(obj).button('loading');
    setTimeout(function () {
        //获取登录模态框里的内容
        var loginID = $('#loginID').val();
        var loginPwd = $('#loginPwd').val();
        var loginCheckCode = $('#loginCheckCode').val();
        var flag = loginIDBlur();
        if(flag != 0){
            $(obj).button('reset');
            return;
        }
        if(typeof loginPwd == "undefined" || loginPwd == null || loginPwd == ""){
            $('#loginMessage').html("密码不能为空");
            $('.loginAlert').fadeIn(0);
            $(obj).button('reset');
            return;
        }
        if(loginPwd.length < 6){
            $('#loginMessage').html("请输入正确的密码");
            $('.loginAlert').fadeIn(0);
            $(obj).button('reset');
            return;
        }
        if(typeof loginCheckCode == "undefined" || loginCheckCode == null || loginCheckCode == ""){
            $('#loginMessage').html("验证码不能为空");
            $('.loginAlert').fadeIn(0);
            $(obj).button('reset');
            return;
        }
        if(loginCheckCode.length != 4){
            $('#loginMessage').html("请输入正确的验证码");
            $('.loginAlert').fadeIn(0);
            $(obj).button('reset');
            return;
        }
        $.ajax({
            type:'POST',
            url:'login/login',
            data:{idCard:loginID,password:loginPwd,checkCode:loginCheckCode},
            dataType:'json',
            success:function (data) {
                if(data.loginStatus == 0){
                    window.location.href = getContextPath() + "/login/loginSuccess";
                }else{
                    if(data.loginStatus == 1){
                        $('#loginMessage').html(data.message);
                        $('.loginAlert').fadeIn(0);
                        changeLoginCheckCode();
                        $(obj).button('reset');
                        return;
                    }
                    if(data.loginStatus == 2){
                        $('#loginMessage').html(data.message);
                        $('.loginAlert').fadeIn(0);
                        changeLoginCheckCode();
                        $(obj).button('reset');
                        return;
                    }
                    if(data.loginStatus == 3){
                        $('#loginMessage').html(data.message);
                        $('.loginAlert').fadeIn(0);
                        changeLoginCheckCode();
                        $(obj).button('reset');
                        return;
                    }
                }
            }
        })
    },500);
}
