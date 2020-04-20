package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Resource
    private ShopService shopService;
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private ProductService productService;

    /**
     * 获取店铺信息以及该店铺下的商品类别列表
     * @param request
     * @return
     */
    @GetMapping("/listshopdetailpageinfo")
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取从前台传过来的shopId
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        //空值判断
        if (shopId != -1L) {
            //获取店铺Id为shopId的店铺信息
            shop = shopService.getByShopId(shopId);
            //获取该店铺下的商品类别列表
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 依据查询条件分页列出该店铺下面的所有商品
     * @param request
     * @return
     */
    @GetMapping("/listproductsbyshop")
    @ResponseBody
    private Map<String, Object> listProductsByShop (HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取shopId
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取每页显示的条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //空值判断
        if (shopId > -1 && pageIndex >-1 && pageSize > -1) {
            //尝试获取要查询的商品类别
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            //尝试获取要查询的商品名称
            String productName = HttpServletRequestUtil.getString(request, "productName");
            //组合查询条件
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            //根据传入的查询条件以及分页信息获取商品列表和商品总数
            ProductExecution productExecution = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", productExecution.getProductList());
            modelMap.put("count", productExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId or empty pageIndex or empty pageSize");
        }
        return modelMap;
    }

    /**
     * 组合查询条件
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        //如果要查询的商品类别不为空
        if (productCategoryId != -1) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //如果要查询的商品名称不为空
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        //前台展示的商品都是允许上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}
