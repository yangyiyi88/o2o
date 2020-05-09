$(function () {
    var productId = getQueryString("productId");
    var isEdit = productId ? true : false;
    var categoryUrl = "/o2o/shopadmin/getproductcategorylist";
    var productInfoUrl = "/o2o/shopadmin/getproductbyid?productId=" + productId;
    var addProductUrl = "/o2o/shopadmin/addproduct";
    var editProductUrl = "/o2o/shopadmin/modifyproduct";

    if(isEdit){
        getProductInitInfo();
    }else {
        getProductCategory();
    }

    //针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片），
    //且控件总数未达到6个，则生成新的一个文件上传控件
    $(".detail-img-div").on("change",".detail-img:last-child",function () {
        if ($(".detail-img").length < 6) {
            $("#detail-img").append('<input type="file" class="detail-img">');
        }
    });

    function getProductCategory() {
        $.getJSON(categoryUrl, function (data) {
            if (data.success){
                var tempProductHtml = "";
                data.productCategoryList.map(function (item, index) {
                    tempProductHtml += "<option data-id='"+item.productCategoryId+"'>"+item.productCategoryName+"</option>";
                });
                $("#product_category").html(tempProductHtml);
            }
        });
    }

    function getProductInitInfo(){
        $.getJSON(productInfoUrl, function (data) {
            if (data.success){
                var product = data.product;
                $("#product_name").val(product.productName);
                $("#priority").val(product.priority);
                $("#point").val(product.point);
                $("#normal_price").val(product.normalPrice);
                $("#promotion_price").val(product.promotionPrice);
                $("#integral").val(product.priority);
                $("#product_desc").val(product.productDesc);
                var productCategoryList = data.productCategoryList;
                var productCategoryHtml = "";
                productCategoryList.map(function (item, index) {
                    productCategoryHtml += "<option data-id='"+item.productCategoryId+"'>"+item.productCategoryName+"</option>";
                });
                $("#product_category").html(productCategoryHtml);
                $("#product-category option[data-id='"+product.productCategory.productCategoryId+"']").attr("selected","selected");
            }
        });
    }

    $("#submit").click(function () {
        var product = {};
        if (isEdit){
            product.productId = productId;
        }
        product.productName = $("#product_name").val();
        product.priority = $("#priority").val();
        product.point = $("#point").val();
        product.normalPrice = $("#normal_price").val();
        product.promotionPrice = $("#promotion_price").val();
        product.productDesc = $("#product_desc").val();
        product.productCategory = {
            productCategoryId : $("#product_category").find("option").not(function () {
                return !this.selected;
            }).data("id")
        };
        var thumbnail = $("#thumbnail")[0].files[0];
        var formDate = new FormData();
        formDate.append("productStr", JSON.stringify(product));
        formDate.append("thumbnail", thumbnail);

        //遍历商品详情图控件，获取里面的文件流
        $(".detail-img").map(function (index, item) {
            //判断该控件是否已选择了文件
            if ($(".detail-img")[index].files.length > 0) {
                //将第i个文件流赋值给key为productImgi的表单键值对里
                formDate.append("productImg"+index, $(".detail-img")[index].files[0]);
            }
        });

        var verifyCodeActual = $("#j_captcha").val();
        if (!verifyCodeActual){
            $.toast("请输入验证码！");
            return;
        }
        formDate.append("verifyCodeActual", verifyCodeActual);

        $.ajax({
            "url" : (isEdit ? editProductUrl : addProductUrl),
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
