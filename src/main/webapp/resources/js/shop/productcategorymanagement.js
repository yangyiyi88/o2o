$(function () {
    var getProductCategoryListUrl = "/o2o/shopadmin/getproductcategorylist";
    var batchAddProductCategoryUrl= "/o2o/shopadmin/batchaddproductcategory";

    getproductcategorylist();

    function getproductcategorylist() {
        $.getJSON(getProductCategoryListUrl, function (data) {
            if (data.success) {
                var html = "";
                data.productCategoryList.map(function (item, index) {
                    html += '<div class="row row-product-category now">'+
                                '<div class="col-33 product-category-name">'+item.productCategoryName+'</div>'+
                                '<div class="col-33">'+item.priority+'</div>'+
                                '<div class="col-33">'+operation(item.productCategoryId)+'</div>'+
                            '</div>'
                });
                $(".category-wrap").html(html);
            }
        });
    }

    function operation(id) {
        return '<a href="/o2o/shopadmin/deleteproductcategory?productCategoryId='+id+'" class="button delete">删除</a>';
    }

    $("#new").click(function () {
        var html = '<div class="row row-product-category temp">'+
                        '<div class="col-33"><input class="category-input category" type="text" placeholder="类别"></div>'+
                        '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'+
                        '<div class="col-33"><a href="/o2o/shopadmin/deleteproductcategory" class="button delete">删除</a></div>'+
                    '</div>'
        $(".category-wrap").append(html);
    });

    $("#submit").click(function () {
        var tempArry = $(".temp");
        var productCategoryList = [];
        tempArry.map(function (index, item) {
            var tempObject = {};
            tempObject.productCategoryName = $(item).find(".category").val();
            tempObject.priority = $(item).find(".priority").val();
            if (tempObject.productCategoryName && tempObject.priority) {
                productCategoryList.push(tempObject);
            }
        });
        $.ajax({
            "url" : batchAddProductCategoryUrl,
            "type" : "post",
            "data" : JSON.stringify(productCategoryList),
            "contentType" : "application/json",
            success : function (data) {
                if (data.success) {
                    $.toast("提交成功!");
                    getproductcategorylist();
                } else {
                    $.toast("提交失败!");
                }
            }
        });
    });

});
