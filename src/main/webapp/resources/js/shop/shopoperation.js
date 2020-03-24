/**
 *
 */
$(function () {
    var initUrl = "/o2o/shopadmin/getshopinitinfo";
    var registerShopUrl = "/o2o/shopadmin/registershop";
    getShopInitInfo();
    alert(initUrl);

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success){
                var tempHtml = "";
                var tempAreaHtml = "";
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += "<option data-id='"+item.shopCategoryId+"'>"+item.shopCategoryName+"</option>";
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += "<option data-id='"+item.areaId+"'>"+item.areaName+"</option>";
                });
                $("#shop-category").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }
        });
    }

    $("#submit").click(function () {
        var shop = {};
        shop.shopName = $("#shop-name").val();
        shop.shopAddr = $("#shop-addr").val();
        shop.phone = $("#shop-phone").val();
        shop.shopDesc = $("#shop-desc").val();
        shop.shopCategory = {
            shopCategoryId : $("#shop-category").find("option").not(function () {
                return !this.selected;
            }).data("id")
        };
        shop.area = {
            areaId : $("#area").find("option").not(function () {
                return !this.selected;
            }).data("id")
        };

        var shopImg = $("#shop-img")[0].files[0];

        var formDate = new FormData();
        formDate.append("shopStr", JSON.stringify(shop));
        formDate.append("shopImg", shopImg);

        var verifyCodeActual = $("#j_captcha").val();
        if (!verifyCodeActual){
            $.toast("请输入验证码！");
            return;
        }
        formDate.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            "url" : registerShopUrl,
            "type" : "POST",
            "data" : formDate,
            "contentType" : false,
            "processData" : false,
            "cache" : false,
            "success" : function (data) {
                if(data.success){
                    $.toast("提交成功！");
                }else {
                    $.toast("提交失败！" + data.errMsg);
                }
                $("#captcha_img").click();
            }
        });
    });

});