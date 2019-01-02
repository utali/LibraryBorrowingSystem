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
    $('.loginAlert').fadeOut(0);
    changeLoginCheckCode();
}
//注册模态框关闭时--清空模态框中数据
function closeRegisterModal() {
    $('#register input').val('');
    $('.registerAlert').fadeOut(0);
    changeRegisterCheckCode();
}

// 更换登录模态框图片验证码
function changeLoginCheckCode() {
    document.getElementById("imgCheckCode").src = getContextPath() + "/login/getLoginImgCheckCode?codeVersion=" + new Date().valueOf();
}
// 更换注册模态框图片验证码
function changeRegisterCheckCode() {
    document.getElementById("registerImgCheckCode").src = getContextPath() + "/login/getRegisterImgCheckCode?codeVersion=" + new Date().valueOf();
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
                    $('#loginMessage').html(data.message);
                    $('.loginAlert').fadeIn(0);
                    changeLoginCheckCode();
                    $(obj).button('reset');
                    return;
                }
            }
        })
    },500);
}

//注册模态框--昵称--失去焦点时，查询数据库
function registerNameBlur() {
    var nameFlag = 0;
    var registerName = $('#registerName').val();
    if(typeof registerName == "undefined" || registerName == null || registerName == ""){
        $('#registerMessage').html("昵称不能为空");
        $('.registerAlert').fadeIn(0);
        nameFlag = 1;
        return nameFlag;
    }
    $.ajax({
        type: 'POST',
        url: 'login/getUserRegisterName',
        data: {name: registerName},
        dataType: 'json',
        async: false,
        success: function (data) {
            console.log(data)
            if (data.status == 1) {
                $('#registerMessage').html(data.message);
                $('.registerAlert').show();
                nameFlag = 2;
            } else {
                $('.registerAlert').hide();
            }
        }
    });
    return nameFlag;
}
//注册模态框--账号--失去焦点时，查询数据库
function registerIDBlur() {
    var IDFlag = 0;
    var registerID = $('#registerID').val();
    if(typeof registerID == "undefined" || registerID == null || registerID == ""){
        $('#registerMessage').html("账号不能为空");
        $('.registerAlert').fadeIn(0);
        IDFlag = 1;
        return IDFlag;
    }
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if(reg.test(registerID) == false){
        $('#registerMessage').html("请输入正确的账号");
        $('.registerAlert').fadeIn(0);
        IDFlag = 2;
        return IDFlag;
    }
    $.ajax({
        type:'POST',
        url:'login/getUserRegisterID',
        data:{idCard:registerID},
        dataType:'json',
        async:false,
        success:function (data) {
            if(data.status == 1){
                $('#registerMessage').html(data.message);
                $('.registerAlert').fadeIn(0);
                IDFlag = 3;
            }else{
                $('.registerAlert').fadeOut(0);
            }
        }
    });
    return IDFlag;
}
// 注册模态框--密码--失去焦点时校验
function registerPwdBlur() {
    var registerPwdFlag = 0;
    var registerPwd = $('#registerPwd').val();
    if(registerPwd.length < 6){
        $('#registerMessage').html('密码少于6位，请重新输入');
        $('.registerAlert').fadeIn(0);
        registerPwdFlag = 1;
        return registerPwdFlag;
    }else{
        $('.registerAlert').fadeOut(0);
        var nameFlag = registerNameBlur();
        if(nameFlag != 0){
            registerPwdFlag = nameFlag + 1;
            return registerPwdFlag;
        }
        var IDFlag = registerIDBlur();
        if(IDFlag != 0){
            registerPwdFlag = IDFlag + 1;
            return registerPwdFlag;
        }
        return registerPwdFlag;
    }
}
// 注册模态框--确认密码--失去焦点时校验
function registerPwdAgainBlur() {
    var registerPwdAgainFlag = 0;
    var registerPwd = $('#registerPwd').val();
    var registerPwdAgain = $('#registerPwdAgain').val();
    if(registerPwd.length < 6){
        $('#registerMessage').html('密码少于6位，请重新输入');
        $('.registerAlert').fadeIn(0);
        registerPwdAgainFlag = 1;
        return registerPwdAgainFlag;
    }else{
        if(registerPwd != registerPwdAgain){
            $('#registerMessage').html('两次输入的密码不一致');
            $('.registerAlert').fadeIn(0);
            registerPwdAgainFlag = 2;
            return registerPwdAgainFlag;
        }
        $('.registerAlert').fadeOut(0);
        var nameFlag = registerNameBlur();
        if(nameFlag != 0){
            registerPwdAgainFlag = nameFlag + 2;
            return registerPwdAgainFlag;
        }
        var IDFlag = registerIDBlur();
        if(IDFlag != 0){
            registerPwdAgainFlag = IDFlag + 2;
            return registerPwdAgainFlag;
        }
        return registerPwdAgainFlag;
    }
}
// 注册
function registerUser(obj) {
    $(obj).button('loading');
    setTimeout(function () {
        // 获取注册模态框里的内容
        var registerName = $('#registerName').val();
        var registerID = $('#registerID').val();
        var registerPwd = $('#registerPwd').val();
        var registerPwdAgain = $('#registerPwdAgain').val();
        var registerCheckCode = $('#registerCheckCode').val();
        var nameFlag = registerNameBlur();
        if(nameFlag != 0){
            $(obj).button('reset');
            return;
        }
        var IDFlag = registerIDBlur();
        if(IDFlag != 0){
            $(obj).button('reset');
            return;
        }
        var registerPwdFlag = registerPwdBlur();
        if(registerPwdFlag != 0){
            $(obj).button('reset');
            return;
        }
        var registerPwdAgainFlag = registerPwdAgainBlur();
        if(registerPwdAgainFlag != 0){
            $(obj).button('reset');
            return;
        }
        if(registerCheckCode.length != 4){
            $('#registerMessage').html('验证码错误');
            $('.registerAlert').fadeIn(0);
            $(obj).button('reset');
            return;
        }
        $.ajax({
            type:'POST',
            url:'login/register',
            data:{name:registerName,idCard:registerID,password:registerPwd,passwordAgain:registerPwdAgain,checkCode:registerCheckCode},
            dataType:'json',
            success:function (data) {
                if(data.registerStatus == 0){
                    window.location.href = getContextPath() + "/login/loginSuccess";
                }else{
                    alert(1111)
                    $('#registerMessage').html(data.message);
                    $('.registerAlert').fadeIn(0);
                    changeRegisterCheckCode();
                    $(obj).button('reset');
                    return;
                }
            }
        })
    },500);
}