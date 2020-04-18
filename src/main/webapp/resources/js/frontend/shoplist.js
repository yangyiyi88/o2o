$(function () {
    //加载flag
    var loading = false;
    //分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 999;
    //一页返回的最大条数
    var pageSize = 3;
    //获取店铺列表的URL
    var shopListUrl = "/o2o/frontend/listshops";
    //获取店铺类别列表以及区域列表的URL
    var searchDivUrl = "/o2o/frontend/listshopspageinfo";
    //页码
    var pageNum = 1;
    //从地址栏URL里尝试获取店铺类别parent的shopCategoryId,也就是parentId
    var parentId = getQueryString("parentId");
    var areaId = "";
    var shopCategoryId = "";
    var shopName = "";
    //渲染出店铺列别列表以及区域列表以供搜索
    getSearchDivData();
    //预先加载3条店铺信息
    addItems(pageSize, pageNum);

    /**
     * 获取店铺类别列表以及区域列表信息
     */
    function getSearchDivData() {
        //如果传入了parentId，则取出此一级类别下面的所有二级类别，否则取出一级大类
        var url = searchDivUrl + "?parentId=" + parentId;
        $.getJSON(url, function (data) {
            if (data.success) {
                var html = "";
                html += '<a href="#" class="button" data-category-id="">全部分类</a>';
                //遍历店铺类别列表，拼接出a标签集
                data.shopCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-category-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</a>';
                });
                $("#shoplist-search-div").html(html);
                var selectOptions = '<option value="">全部街道</option>';
                //遍历区域信息列表，拼接出option标签集
                data.areaList.map(function (item, index) {
                    selectOptions += '<option value="' + item.areaId + '">' + item.areaName + '</option>';
                });
                $("#area-search").html(selectOptions);
            }
        });
    }

    /**
     * 获取分页展示的店铺列表信息
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        //拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
        var url = shopListUrl + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&parentId=" + parentId
            + "&areaId=" + areaId + "&shopCategoryId=" + shopCategoryId + "&shopName=" + shopName;
        //设定加载符，若还在后台取数据则不能再次访问后台，避免对此重复加载
        loading = true;
        //访问后台获取相应条件下的店铺列表
        $.getJSON(url, function (data) {
            if (data.success) {
                //获取当前查询条件下店铺的总数
                maxItems = data.count;
                var html = "";
                //遍历店铺列表，拼接出卡片集合
                data.shopList.map(function (item, index) {
                    html += '<div class="card" data-shop-id="' + item.shopId + '">' +
                        '<div class="card-header">' + item.shopName + '</div>' +
                        '<div class="card-content">' +
                        '<div class="list-block media-list">' +
                        '<ul>' +
                        '<li class="item-content">' +
                        '<div class="item-media">' +
                        '<img src="' + item.shopImg + '"' +
                        '    width="44">' +
                        '</div>' +
                        '<div class="item-inner">' +
                        '<div class="item-subtitle">' + item.shopDesc + '</div>' +
                        '</div>' +
                        '</li>' +
                        '</ul>' +
                        '</div>' +
                        '</div>' +
                        '<div class="card-footer">' +
                        '<span>' + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '更新</span>' +
                        '<span>点击查看</span>' +
                        '</div> ' +
                        '</div>';
                });
                $(".list-div").append(html);
                //获取目前为止已显示的卡片总数，包含之前已经加载的
                var total = $(".list-div .card").length;
                //若总数和根据此查询条件列出的总数一致，则停止后台的加载
                if (total >= maxItems) {
                    // 隐藏提示符
                    $('.infinite-scroll-preloader').hide();
                    return;
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                //否则页码加1，继续load出新的店铺
                pageNum += 1;
                //加载结束，可以再次加载了
                loading = false;
                //刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

    //下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom',function() {
        // 如果正在加载，则退出
        if (loading) return;
        addItems(pageSize, pageNum);
    });

    //点击店铺的卡片进入该店铺的详情页
    $(".shop-list").on("click", ".card", function (e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = "/o2o/frontend/shopdetail?shopId=" + shopId;
    });

    //选择新的店铺类别之后，重置页码，清空原先的店铺列表，按照新的类别去查询
    $("#shoplist-search-div").on("click", ".button", function (e) {
        if (parentId) {//parentId不为空，说明是该一级类别下的二级id
            shopCategoryId = e.currentTarget.dataset.categoryId;
            if ($(e.currentTarget).hasClass("button-fill")) {//说明此类别之前已被选定，click变为不选定
                $(e.currentTarget).removeClass("button-fill");
                shopCategoryId = "";
            } else {//说明此类别之前没被选定，click变为选定，将其他类别变为不选定
                $(e.currentTarget).addClass("button-fill").siblings().removeClass("button-fill");
            }
            //由于查询条件改变，清空店铺列表再进行查询
            $(".list-div").empty();
            //重置页码
            pageNum = 1;
            addItems(pageSize, pageNum);
        } else {//如果parentId为空，说明是一级类别
            parentId = e.currentTarget.dataset.categoryId;
            if ($(e.currentTarget).hasClass("button-fill")) {
                $(e.currentTarget).removeClass("button-fill");
                parentId = "";
            } else {
                $(e.currentTarget).addClass("button-fill").siblings().removeClass("button-fill");
            }
            //由于查询条件改变，清空店铺列表再进行查询
            $(".list-div").empty();
            //重置页码
            pageNum = 1;
            addItems(pageSize, pageNum);
        }
    });

    //需要查询的店铺名称发生变化后，重置页码，清空原先的店铺列表，按照新的名字去查询
    $("#search").on("change", function (e) {
        shopNam = e.target.value;
        $(".list-div").empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 区域信息发生变化后，重置页码，清空原先的店铺列表，按照新的区域去查询
    $('#area-search').on('change', function() {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });

    // 初始化页面
    $.init();
});
