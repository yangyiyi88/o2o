package com.imooc.o2o.service;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exception.ProductOperationException;

import java.io.File;
import java.util.List;

public interface ProductService {
    /**
     * 添加商品信息以及缩略图、商品详情图的处理
     * @param product 商品信息
     * @param thumbnail 缩略图
     * @param productImgFileList 商品详情图
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct (Product product, File thumbnail, List<File> productImgFileList) throws ProductOperationException;

    /**
     * 根据商品Id查询唯一的商品信息
     * @param productId
     * @return
     * @throws ProductOperationException
     */
    Product getProductById(long productId);

    /**
     * 分页查询商品列表，可输入的查询条件有：商品名（模糊），商品状态， 店铺ID，商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 修改商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgFileList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product, File thumbnail, List<File> productImgFileList) throws ProductOperationException;
}
