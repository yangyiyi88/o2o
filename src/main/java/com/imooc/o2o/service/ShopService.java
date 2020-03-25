package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exception.ShopOperationException;

import java.io.File;

public interface ShopService {
    /**
     * 添加店铺，包括添加店铺图片
     * @param shop
     * @param imgFile
     * @return
     */
    ShopExecution addShop(Shop shop, File imgFile) throws ShopOperationException;

    /**
     * 通过店铺ID获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(Long shopId);

    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop
     * @param imgFile
     * @return
     */
    ShopExecution modifyShop(Shop shop, File imgFile) throws ShopOperationException;
}
