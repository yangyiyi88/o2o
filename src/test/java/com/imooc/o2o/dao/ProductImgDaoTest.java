package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;
    @Test
    public void testABatchInsertProductImg(){
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("详情图地址1");
        productImg1.setImgDesc("详情图描述1");
        productImg1.setPriority(100);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("详情图地址2");
        productImg2.setImgDesc("详情图描述2");
        productImg2.setPriority(100);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testBQueryProductImgByProductId(){
        long productId = 1L;
        List<ProductImg> productImgList = productImgDao.queryProductImgByProductId(productId);
        assertEquals(2, productImgList.size());
    }

    @Test
    public void testCDeleteProductImgByProductId(){
        long productId = 1L;
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }
}
