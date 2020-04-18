package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory(){
//        List<ShopCategory> list = shopCategoryDao.queryShopCategory(new ShopCategory());
//        assertEquals(13, list.size());

        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parentshopCategory = new ShopCategory();
        parentshopCategory.setShopCategoryId(12L);
        shopCategory.setParent(parentshopCategory);
        System.out.println("shopCategory parent id :" + shopCategory.getParent().getShopCategoryId());
        List<ShopCategory> list2 = shopCategoryDao.queryShopCategoryList(shopCategory);
        assertEquals(3,list2.size());

        List<ShopCategory> list3 = shopCategoryDao.queryShopCategoryList(null);
        assertEquals(6, list3.size());
    }
}
