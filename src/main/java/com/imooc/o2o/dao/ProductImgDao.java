package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图片
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 列出某个商品的详情图列表
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgByProductId(long productId);
}
