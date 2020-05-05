$(function () {
    $.getJSON("/o2o/local/getauthusername", function (data) {
        if (data.success) {
            $("#username").val(data.username);
            $("#username").attr("disabled","disabled");
        }
    });

    var changepswUrl = "/o2o/local/changepsw";
    //从地址栏的URL获取usertype，usertype=1则为customer，其余为shopowner
    var usertype = getQueryString("usertype");

    $("#submit").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        var newPassword = $("#newPassword").val();
        var confirmPassword = $("#confirmPassword").val();
        var verifyCodeActual = $("#j_captcha").val();

        //添加表单数据
        var formDate = new FormData();
        formDate.append("username", username);
        formDate.append("password", password);
        formDate.append("newPassword", newPassword);
        formDate.append("verifyCodeActual", verifyCodeActual);

        if (!verifyCodeActual) {
            $.toast("请输入验证码！");
            return;
        }

        if (newPassword != confirmPassword) {
            $.toast("两次输入新密码不相同！");
            return;
        }

        if (newPassword == password) {
            $.toast("新旧密码不能相同！");
            return;
        }

        $.ajax({
            "url": changepswUrl,
            "type": "post",
            "data": formDate,
            "cache": false,
            processData: false,   // jQuery不要去处理发送的数据
            contentType: false,   // jQuery不要去设置Content-Type请求头
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功！");
                    if (usertype == 1) {
                        window.location.href = "/o2o/frontend/index";
                    } else {
                        window.location.href = "/o2o/shopadmin/shoplist";
                    }
                } else {
                    $.toast("提交失败！" + data.errMsg);
                    //更换验证码
                    $("#captcha_img").click();
                }
            }
        });
    });

    $("#back").click(function () {
        window.location.href = "/o2o/shopadmin/shoplist";
    });
});
