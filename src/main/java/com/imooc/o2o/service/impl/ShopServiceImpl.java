package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service("shopService")
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    private Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Transactional
    public ShopExecution addShop(Shop shop, File imgFile) throws ShopOperationException{
        //空值判断
        if (shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给shop赋初始值
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            //添加shop
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum > 0) {
                //店铺添加成功，添加imgFile
                if (imgFile != null) {
                    //根据店铺ID添加图片，并将图片存储的相对值路径添加到shop中
                    addShopImg(shop, imgFile);
                    //图片添加成功后，更新shop
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <= 0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                } else {
                    throw new ShopOperationException("图片为空");
                }
            } else {
                throw new ShopOperationException("店铺添加失败");
            }
        }catch (Exception e){
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    /**
     * 根据shop的ID存储图片，并将图片存储的相对值路径存到shop中
     * @param shop
     * @param imgFile
     */
    public void addShopImg(Shop shop, File imgFile){
        //根据shop的ID获取要存储的相对值路径
        String targetAddr = PathUtil.getShopImagePath(shop.getShopId());
        //处理图片，并获得相对值路径
        String relativeAddr = ImageUtil.generateThumbnail(imgFile, targetAddr);
        logger.debug("addShopImg imgFile.getName():" + imgFile.getName());
        shop.setShopImg(relativeAddr);
    }

    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    public ShopExecution modifyShop(Shop shop, File imgFile) throws ShopOperationException{
        //1.空值判断
        if (shop != null && shop.getShopId() != null){
            try{
                //2.判断是否需要处理图片
                //如果传入的imgFile不为空
                if (imgFile != null){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    String shopImgAddr= tempShop.getShopImg();
                    if (shopImgAddr != null){
                        //将图片原路径及图片删除
                        ImageUtil.deleteFileOrPath(shopImgAddr);
                        //根据店铺ID存储新的图片，并将图片存储的相对值路径添加到shop中
                        addShopImg(shop, imgFile);
                    }
                }
                //3.更新店铺
                //给shop赋初始值
                shop.setLastEditTime(new Date());
                //更新
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            }catch (Exception e){
                throw  new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }else {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
    }

    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        ShopExecution shopExecution = new ShopExecution();
        //利用工具类类将页码转换成行码
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        //依据查询条件，调用dao层返回相关的店铺列表
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        //依据相同的查询条件，返回店铺总数
        int count = shopDao.queryShopCount(shopCondition);
        if (shopList != null) {
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        } else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

}
