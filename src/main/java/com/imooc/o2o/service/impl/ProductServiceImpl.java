package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exception.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 1.处理缩略图，获取缩略图相对值路径并赋值给product
     * 2.往tb_product表中写入商品信息，获取productId
     * 3.结合productId批量处理商品详情图（给商品详情图productId赋值）
     * 4.将商品详情图列表批量插入tb_product_img表中（存储路径都是相应店铺地下）
     *
     * @param product            商品信息
     * @param thumbnail          缩略图
     * @param productImgFileList 商品详情图文件列表
     * @return
     * @throws ProductOperationException
     */
    public ProductExecution addProduct(Product product, File thumbnail, List<File> productImgFileList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给product设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架状态
            product.setEnableStatus(1);
            //若商品缩略图不为空则添加
            if (thumbnail != null) {
                try {
                    addThumbnail(product, thumbnail);
                } catch (Exception e) {
                    throw new ProductOperationException("添加缩略图失败：" + e.getMessage());
                }
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败：" + e.getMessage());
            }
            //若商品详情图不为空则添加
            if (productImgFileList != null && productImgFileList.size() > 0) {
                try {
                    addProductImgList(product, productImgFileList);
                } catch (Exception e) {
                    throw new ProductOperationException("添加商品详情图失败：" + e.getMessage());
                }
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            //传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPT);
        }
    }

    /**
     * 添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, File thumbnail) {
        //根据shopId获取存储路径
        String targetAddr = PathUtil.getShopImagePath(product.getShop().getShopId());
        //处理缩略图，返回新生成图片的相对值路径
        String relativeAddr = ImageUtil.generateThumbnail(thumbnail, targetAddr);
        //将新生成图片的相对值路径赋值给product
        product.setImgAddr(relativeAddr);
    }

    /**
     * 批量添加商品详情图
     *
     * @param product
     * @param productImgFileList
     */
    private void addProductImgList(Product product, List<File> productImgFileList) {
        //根据shopId获取存储路径
        String targetAddr = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        //遍历图片文件，依次处理并添加到productImg实体类里
        for (File productImgFile : productImgFileList) {
            //处理商品详情图，返回新生成图片的相对值路径
            String relativeAddr = ImageUtil.generateNormalImg(productImgFile, targetAddr);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(relativeAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果确实是有图片需要添加的，就执行批量添加操作
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图失败！");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图失败:" + e.getMessage());
            }
        }
    }
}
