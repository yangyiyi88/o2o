$(function () {
    //从地址栏的URL获取productId
    var productId = getQueryString("productId");
    //获取商品信息的URL
    var productUrl = "/o2o/frontend/listproductdetailpageinfo?productId=" + productId;

    //访问后台获取该商品信息并渲染
    $.getJSON(productUrl, function (data) {
        if (data.success) {
            //获取商品信息
            var product = data.product;
            //给商品信息相关的HTML控件赋值
            $("#product-img").attr("src", product.imgAddr);
            $("#product-time").text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
            $("#product-name").text(product.productName);
            $("#product-desc").text(product.productDesc);
            //购买可得积分
            if (product.point != undefined) {
                $("#product-point").text("购买可得" + product.point + "积分");
            }
            //商品价格展示逻辑，主要判断原价现价是否为空，所有都为空则不显示价格栏目
            if (product.normalPrice != undefined && product.promotionPrice != undefined) {
                //如果原价和现价都不为空则都展示，并且给原价加个删除符号
                $("#price").show();
                $("#normalPrice").html("<del>" + "¥" + product.normalPrice + "</del>");
                $("#promotionPrice").text("¥" + product.promotionPrice);
            } else if (product.normalPrice != undefined && product.promotionPrice == undefined) {
                //如果原价不为空而现价为空则只展示原价
                $("#price").show();
                $("#normalPrice").text("¥" + product.normalPrice);
            } else if (product.normalPrice == undefined && product.promotionPrice != undefined) {
                //如果原价为空而现价不为空则只展示现价
                $("#price").show();
                $("#promotionPrice").text("¥" + product.promotionPrice);
            }
            //遍历商品详情图列表
            var imgListHtml = "";
            product.productImgList.map(function (item, index) {
                imgListHtml += '<div><img src="' + item.imgAddr + '" width="100%"/></div>';
            });
            $("#imgList").html(imgListHtml);
        }
    });

    //点击后打开右侧栏
    $("#me").click(function () {
        $.openPanel("#panel-right-demo");
    });

    // 初始化页面
    $.init();
});
