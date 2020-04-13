$(function () {
    //获取此店铺下的商品列表的URL
    var getProductListUrl = "/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=99";
    //商品下架的URL
    var statusUrl = "/o2o/shopadmin/modifyproduct";

    getproductlist();

    function getproductlist() {
        $.getJSON(getProductListUrl, function (data) {
            if (data.success) {
                var html = "";
                //遍历每条商品信息，拼接成一行显示，列信息包括：
                //商品名称，优先级，上架\下架（含productId），编辑（含productId），预览（含productId）
                data.productList.map(function (item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        textOp = "上架";
                        contraryStatus = 1;
                    }
                    html += '<div class="row row-product">'+
                        '<div class="col-33 product-name">'+item.productName+'</div>'+
                        '<div class="col-20">'+item.priority+'</div>'+
                        '<div class="col-40">' +
                        '<a href="#" class="edit" data-id="'+item.productId+'">编辑</a>' +
                        '<a href="#" class="status" data-id="'+item.productId+'" data-status="'+contraryStatus+'">'+textOp+'</a>'+
                        '<a href="#" class="preview" data-id="'+item.productId+'">预览</a>' +
                        '</div>'+
                        '</div>'
                });
                $(".product-wrap").html(html);
            }
        });
    }

    //将class为product-wrap里面的a标签绑定上点击事件
    $(".product-wrap").on("click", "a", function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass("edit")) {
            window.location.href = "/o2o/shopadmin/productoperation?productId=" + e.currentTarget.dataset.id;
        }
        if (target.hasClass("status")) {
            changeItemStatus(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
        }
        if (target.hasClass("preview")) {
            window.location.href = "/o2o/frontend/productdetail?productId=" + e.currentTarget.dataset.id ;
        }
    });

    function changeItemStatus(id, enableStatus) {
        //定义product json对象并添加productId以及状态（上架\下架）
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm("确定吗？", function () {
            $.ajax({
                "url" : statusUrl,
                "type" : "post",
                "data" : {
                    productStr : JSON.stringify(product),
                    statusChange : true
                },
                success : function (data) {
                    if (data.success) {
                        $.toast("操作成功！");
                        getproductlist();
                    } else {
                        $.toast("操作失败!");
                    }
                }
            });
        });
    }
});
