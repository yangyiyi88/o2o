package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testInsertProduct(){
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setImgAddr("缩略图地址");
        product.setPriority(200);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);
        int effectedNum = productDao.insertProduct(product);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryProductByProductId(){
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("商品详情图地址1");
        productImg1.setImgDesc("商品详情图地址1");
        productImg1.setPriority(100);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("商品详情图地址2");
        productImg2.setImgDesc("商品详情图地址2");
        productImg2.setPriority(100);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectedNum);
        Product product = productDao.queryProductByProductId(1L);
        assertEquals(2, product.getProductImgList().size());
        effectedNum = productImgDao.deleteProductImgByProductId(1L);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testUpdateProduct(){
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductId(13L);
        product.setProductName("正式商品1");
        product.setProductDesc("正式商品1");
        product.setImgAddr("正式缩略图地址");
        product.setPriority(300);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1, effectedNum);
    }
}
