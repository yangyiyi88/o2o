$(function () {
    var logincheckUrl = "/o2o/local/logincheck";
    //从地址栏的URL里获取usertype,usertype=1为customer，其余为shopowner
    var userType = getQueryString("usertype");
    //登陆次数，积累登陆三次失败之后自动弹出验证码要求输入
    var loginCount = 0;

    $("#submit").click(function () {
        var username = $("#username").val();
        var password = $("#psw").val();
        var verifyCodeActual = $("#j_captcha").val();
        //是否需要验证码，默认为false
        var needVerify = false;
        //如果登陆三次都失败
        if (loginCount >= 3) {
            //那么就需要验证码校验了
            if (!verifyCodeActual) {
                $.toast("请输入验证码！");
                return;
            } else {
                needVerify = true;
            }
        }

        $.ajax({
            "url" : logincheckUrl,
            "async" : false,
            "cache" : false,
            "type" : "post",
            "data" : {
                username : username,
                password : password,
                verifyCodeActual : verifyCodeActual,
                needVerify : needVerify
            },
            success : function (data) {
                if (data.success) {
                    if (userType == 1) {
                        //如果用户在前端展示系统页面则自动连接到前端展示系统首页
                        window.location.href = "/o2o/frontend/index";
                    } else {
                        //如果用户在店家管理系统页面则自动链接到店铺列表页中
                        window.location.href = "/o2o/shopadmin/shoplist";
                    }
                } else {
                    $.toast("登陆失败！" + data.errMsg);
                    loginCount ++;
                    if (loginCount >= 3) {
                        //登陆失败三次，需要做验证码校验
                        $("#verifyPart").show();
                    }
                }
            }
        });
    });
});
