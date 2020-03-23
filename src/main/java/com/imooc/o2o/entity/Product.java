package com.imooc.o2o.entity;

import java.util.Date;
import java.util.List;

public class Product {
    /*
    +---------------------+---------------+------+-----+---------+----------------+
| Field               | Type          | Null | Key | Default | Extra          |
+---------------------+---------------+------+-----+---------+----------------+
| product_id          | int(100)      | NO   | PRI | NULL    | auto_increment |
| product_name        | varchar(100)  | NO   |     | NULL    |                |
| product_desc        | varchar(2000) | YES  |     | NULL    |                |
| img_addr            | varchar(2000) | YES  |     |         |                |
| normal_price        | varchar(100)  | YES  |     | NULL    |                |
| promotion_price     | varchar(100)  | YES  |     | NULL    |                |
| priority            | int(2)        | NO   |     | 0       |                |
| create_time         | datetime      | YES  |     | NULL    |                |
| last_edit_time      | datetime      | YES  |     | NULL    |                |
| enable_status       | int(2)        | NO   |     | 0       |                |
| product_category_id | int(11)       | YES  | MUL | NULL    |                |
| shop_id             | int(20)       | NO   | MUL | 0       |                |
+---------------------+---------------+------+-----+---------+----------------+
     */
    //商品ID
    private Long productId;
    //商品名称
    private String productName;
    //商品描述
    private String productDesc;
    //商品缩略图地址
    private String imgAddr;
    //商品原价
    private String normalPrice;
    //商品促销价 现价
    private String promotionPrice;
    //商品权重
    private Integer priority;
    //商品创建时间
    private Date createTime;
    //最近更新时间
    private Date lastEditTime;
    //商品状态 0：下架 1：在前端展示系统展示
    private Integer enableStatus;
    //商品类别
    private ProductCategory productCategory;
    //商品是哪个店铺发布的
    private Shop shop;
    //商品详情图片列表  商品和商品详情图片是一对多的关系
    private List<ProductImg> productImgList;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public void setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
    }
}
