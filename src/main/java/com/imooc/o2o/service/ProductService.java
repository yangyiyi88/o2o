package com.imooc.o2o.service;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
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
}
