package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Resource
    private ProductService productService;

    @Test
    public void testAddProduct() throws ShopOperationException, FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品2");
        product.setProductDesc("测试商品2");
        product.setPriority(200);
        File thumbnail = new File("/Users/yangkun/Pictures/image/xiaohuangren.jpg");
        File productImgFile1 = new File("/Users/yangkun/Pictures/image/xiaohuangren.jpg");
        File productImgFile2 = new File("/Users/yangkun/Pictures/image/dabai.jpg");
        List<File> productImgFileList = new ArrayList<File>();
        productImgFileList.add(productImgFile1);
        productImgFileList.add(productImgFile2);
        ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgFileList);
        assertEquals(1, productExecution.getState());
    }

    @Test
    public void testModifyProduct() throws ShopOperationException{
        Product product = new Product();
        product.setProductId(14L);
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品2");
        product.setProductDesc("测试商品2");
        product.setPriority(200);
        File thumbnail = new File("/Users/yangkun/Pictures/image/xiaohuangren.jpg");
        File productImgFile1 = new File("/Users/yangkun/Pictures/image/dabai.jpg");
        File productImgFile2 = new File("/Users/yangkun/Pictures/image/xiaohuangren.jpg");
        List<File> productImgFileList = new ArrayList<File>();
        productImgFileList.add(productImgFile1);
        productImgFileList.add(productImgFile2);
        ProductExecution productExecution = productService.modifyProduct(product, thumbnail, productImgFileList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
    }
}
