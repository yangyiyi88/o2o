package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exception.ProductOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("productManagementController")
@RequestMapping("/shopadmin")
public class ProductManagementController {
    //文件上传解析器
    @Resource
    private CommonsMultipartResolver commonsMultipartResolver;
    @Resource
    private ProductService productService;
    @Resource
    private ProductCategoryService productCategoryService;

    //支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    @GetMapping("/getproductbyid")
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productId > -1){
            //获取商品信息
            Product product = productService.getProductById(productId);
            //获取该店铺下商品的所有分类
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }

    @PostMapping("/addproduct")
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //1.验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //2.接收并转化参数，包括商品、缩略图、详情图列表
        //接收商品信息
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            if (productStr != null) {
                product = mapper.readValue(productStr, Product.class);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "请输入商品信息！");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //接收图片
        File thumbnailFile = null;
        List<File> productImgFileList = new ArrayList<File>();
        try {
            //若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (commonsMultipartResolver.isMultipart(request)) {
                thumbnailFile = handleImage(request, thumbnailFile, productImgFileList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空！");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //3.若商品信息、缩略图及详情图片列表为非空，则开始进行商品添加操作
        if (product != null && thumbnailFile != null && productImgFileList.size() > 0) {
            try {
                //从session中获取当前店铺的ID并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行操作
                ProductExecution productExecution = productService.addProduct(product, thumbnailFile, productImgFileList);
                if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productExecution.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @PostMapping("/modifyproduct")
    @ResponseBody
    public Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //1.验证码校验
        //判断是商品编辑时候调用还是上下架操作的时候调用，若为前者进行验证码判断，后者则跳过验证码判断
        Boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //2.接收并转化参数，包括商品、缩略图、详情图列表
        //接收商品信息
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            if (productStr != null) {
                product = mapper.readValue(productStr, Product.class);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "请输入商品信息！");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //接收图片
        File thumbnailFile = null;
        List<File> productImgFileList = new ArrayList<File>();
        try {
            //若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (commonsMultipartResolver.isMultipart(request)) {
                thumbnailFile = handleImage(request, thumbnailFile, productImgFileList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //3.若商品信息、缩略图及详情图片列表为非空，则开始进行商品添加操作
        if (product != null && product.getProductId() != null) {
            try {
                //从session中获取当前店铺的ID并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行操作
                ProductExecution productExecution = productService.modifyProduct(product, thumbnailFile, productImgFileList);
                if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productExecution.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    private File handleImage(HttpServletRequest request, File thumbnailFile, List<File> productImgFileList) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        //取出缩略图
        MultipartFile multipartFile1 = multipartHttpServletRequest.getFile("thumbnail");
        if (multipartFile1 != null) {
            thumbnailFile = ImageUtil.transferMultipartFileToFile(multipartFile1);
        }
        //取出详情图列表，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            MultipartFile multipartFile2 = multipartHttpServletRequest.getFile("productImg" + i);
            if (multipartFile2 != null) {
                //若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                File productImgFile = ImageUtil.transferMultipartFileToFile(multipartFile2);
                productImgFileList.add(productImgFile);
            } else {
                //若取出的第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnailFile;
    }
}
