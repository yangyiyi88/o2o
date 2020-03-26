package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.impl.ShopServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Resource
    private ShopService shopService;

    @Test
    public void testAddShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File imgFile = new File("/Users/yangkun/Pictures/image/xiaohuangren.jpg");
        ShopExecution shopExecution = shopService.addShop(shop, imgFile);
        assertEquals(0,shopExecution.getState());
    }

    @Test
    public void testModifyShop()throws ShopOperationException {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("修改后的店铺名称");
        File imgFile = new File("/Users/yangkun/Pictures/image/xiaohuangren.jpg");
        ShopExecution shopExecution = shopService.modifyShop(shop, imgFile);
        assertEquals(1,shopExecution.getState());
    }

    @Test
    public void testGetShopList() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        ShopExecution shopExecution = shopService.getShopList(shopCondition, 0, 5);
        System.out.println("店铺列表的大小：" + shopExecution.getShopList().size());
        System.out.println("店铺的总数：" + shopExecution.getCount());
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        shopCondition.setShopCategory(shopCategory);
        shopExecution = shopService.getShopList(shopCondition, 0, 2);
        System.out.println("xin店铺列表的大小：" + shopExecution.getShopList().size());
        System.out.println("xin店铺的总数：" + shopExecution.getCount());
    }
}
