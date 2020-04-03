package com.imooc.o2o.entity;

import java.util.Date;

public class ProductImg {
    /*
    +----------------+---------------+------+-----+---------+----------------+
| Field          | Type          | Null | Key | Default | Extra          |
+----------------+---------------+------+-----+---------+----------------+
| product_img_id | int(20)       | NO   | PRI | NULL    | auto_increment |
| img_addr       | varchar(2000) | NO   |     | NULL    |                |
| img_desc       | varchar(2000) | YES  |     | NULL    |                |
| priority       | int(2)        | YES  |     | 0       |                |
| create_time    | datetime      | YES  |     | NULL    |                |
| product_id     | int(20)       | YES  | MUL | NULL    |                |
+----------------+---------------+------+-----+---------+----------------+
     */
    //商品详情图片ID
    private Long productImgId;
    //商品详情图片地址
    private String imgAddr;
    //商品详情图片说明
    private String imgDesc;
    //权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //商品ID 商品详情图片是属于哪一个商品的，
    private Long productId;

    public Long getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Long productImgId) {
        this.productImgId = productImgId;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
