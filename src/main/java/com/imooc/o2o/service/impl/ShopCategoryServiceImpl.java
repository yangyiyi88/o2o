package com.imooc.o2o.service.impl;

import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.ShopCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Resource
    private ShopCategoryService shopCategoryService;

    public List<ShopCategory> getShopCategory(ShopCategory shopCategory) {
        return shopCategoryService.getShopCategory(new ShopCategory());
    }
}
