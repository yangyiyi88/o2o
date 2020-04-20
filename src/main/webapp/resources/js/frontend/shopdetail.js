$(function () {
    //从地址栏获取shopId
    var shopId = getQueryString("shopId");
    //查询店铺信息的Url
    var shopInfoUrl = "/o2o/frontend/listshopdetailpageinfo?shopId=" + shopId;
    //根据查询条件分页查询该店铺下的商品的Url
    var productListUrl = "/o2o/frontend/listproductsbyshop?shopId=" + shopId;
    //定义加载符
    var loading = false;
    //默认一页显示的商品数
    var pageSize = 3;
    //获取的商品最大数量，超过此数禁止访问后台
    var maxItems = 100;
    //默认的页码
    var pageNum = 1;
    var productCategoryId = "";
    var productName = "";
    //渲染出店铺基本信息以及商品类别列表以供搜索
    getShopInfo();
    //预先加载3条商品信息
    addItems(pageNum, pageSize);

    //获取店铺基本信息以及商品类别列表
    function getShopInfo() {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                //获取店铺信息，并添加到html控件
                var shop = data.shop;
                $("#shop-name").text(shop.shopName);
                $("#shop-img").attr("src", shop.shopImg);
                $("#shop-update-time").html(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
                $("#shop-desc").text(shop.shopDesc);
                $("#shop-addr").text(shop.shopAddr);
                $("#shop-phone").text(shop.phone);
                //获取商品分类列表，并添加到html控件
                var productCategoryList = data.productCategoryList;
                var html = "";
                productCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-category-id="' + item.productCategoryId + '">' + item.productCategoryName + '</a>';
                });
                $("#productlist-search-div").html(html);
            }
        });
    }

    /**
     * 获取分页展示的商品列表信息
     * @param pageIndex
     * @param pageSize
     */
    function addItems(pageIndex, pageSize) {
        var url = productListUrl + "&pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&productCategoryId=" + productCategoryId +
            "&productName=" + productName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                var productList = data.productList;
                maxItems = data.count;
                var productHtml = "";
                productList.map(function (item, index) {
                    productHtml += '<div class="card" data-product-id="' + item.productId + '">' +
                        '<div class="card-header">' + item.productName + '</div>' +
                        '<div class="card-content">' +
                        '<div class="list-block media-list">' +
                        '<ul>' +
                        '<li class="item-content">' +
                        '<div class="item-media">' +
                        '<img src="' + item.imgAddr + '" width="44">' +
                        '</div>' +
                        '<div class="item-inner">' +
                        '<div class="item-subtitle">' + item.productDesc + '</div>' +
                        '</div>' +
                        ' </li>' +
                        ' </ul>' +
                        ' </div>' +
                        ' </div>' +
                        ' <div class="card-footer">' +
                        '<span>' + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '更新</span>' +
                        '<span>点击查看</span>' +
                        '</div>' +
                        '</div>';
                });
                $(".list-div").append(productHtml);
                var totalSize = $(".list-div .card").length;
                if (totalSize >= maxItems) {
                    $(".infinite-scroll-preloader").hide();
                    return;
                } else {
                    $(".infinite-scroll-preloader").show();
                }
                pageNum += 1;
                loading = false;
                $.refreshScroller();
            }
        });
    }

    //向下滑动屏幕，自动进行分页搜索
    $(document).on("infinite", ".infinite-scroll-bottom", function () {
        if (loading) return;
        addItems(pageNum, pageSize);
    });

    //选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
    $("#productlist-search-div").on("click", ".button", function (e) {
        productCategoryId = e.currentTarget.dataset.categoryId;
        if ($(e.currentTarget).hasClass("button-fill")) {//说明此类别之前已被选定，点击变为不选定
            $(e.currentTarget).removeClass("button-fill");
            productCategoryId = "";
        } else {//说明此类别之前没被选定，点击变为选定
            $(e.currentTarget).addClass("button-fill").siblings().removeClass("button-fill");
        }
        //由于查询条件改变，清空商品列表再进行查询
        $(".list-div").empty();
        //重置页码
        pageNum = 1;
        addItems(pageNum, pageSize);
    });

    //需要查询的商品名称发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
    $("#search").on("change", function (e) {
        productName = e.target.value;
        $(".list-div").empty();
        pageNum = 1;
        addItems(pageNum, pageSize);
    });

    //点击商品的卡片进入该商品的详情页
    $(".list-div").on("click", ".card", function (e) {
        var productId = e.currentTarget.dataset.productId;
        window.location.href = "/o2o/frontend/productdetail?productId=" + productId;
    });

    //点击打开侧栏
    $("#me").click(function () {
        $.openPanel("#panel-right-demo");
    });

    //初始化
    $.init();
});
