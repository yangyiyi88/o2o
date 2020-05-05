$(function () {
    var logoutUrl = "/o2o/local/logout";

    $("#log-out").click(function () {
        $.ajax({
            "url" : logoutUrl,
            "type" : "post",
            "async" : false,
            "cache" : false,
            success : function (data) {
                if (data.success) {
                    var usertype = $("#log-out").attr("usertype");
                    //清除成功后退出到登陆界面
                    window.location.href = "/o2o/local/login?usertype=" + usertype;
                    return false;
                }
            },
            error : function (data, error) {
                alert(error);
            }
        });
    });
});
