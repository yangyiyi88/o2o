package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;

public interface ProductDao {
    /**
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 通过productId查询唯一的商品信息
     * @param productId
     * @return
     */
    Product queryProductByProductId(long productId);

    /**
     * 更新商品信息
     * @param product
     * @return
     */
    int updateProduct(Product product);
}
