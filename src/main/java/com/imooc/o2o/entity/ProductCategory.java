package com.imooc.o2o.entity;

import java.util.Date;

public class ProductCategory {
    /*
    +-----------------------+--------------+------+-----+---------+----------------+
| Field                 | Type         | Null | Key | Default | Extra          |
+-----------------------+--------------+------+-----+---------+----------------+
| product_category_id   | int(11)      | NO   | PRI | NULL    | auto_increment |
| product_category_name | varchar(100) | NO   |     | NULL    |                |
| priority              | int(2)       | YES  |     | 0       |                |
| create_time           | datetime     | YES  |     | NULL    |                |
| shop_id               | int(20)      | NO   | MUL | 0       |                |
+-----------------------+--------------+------+-----+---------+----------------+
     */
    //商品类别ID
    private Long productCategoryId;
    //商品类别名称
    private String productCategoryName;
    //权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //店铺ID 表明商品类别是哪一个店铺下的
    private Long shopId;

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
