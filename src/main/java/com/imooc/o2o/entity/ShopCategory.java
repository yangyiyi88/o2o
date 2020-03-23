package com.imooc.o2o.entity;

import java.util.Date;

public class ShopCategory {
    /*
    +--------------------+---------------+------+-----+---------+----------------+
| Field              | Type          | Null | Key | Default | Extra          |
+--------------------+---------------+------+-----+---------+----------------+
| shop_category_id   | int(11)       | NO   | PRI | NULL    | auto_increment |
| shop_category_name | varchar(100)  | NO   |     |         |                |
| shop_category_desc | varchar(1000) | YES  |     |         |                |
| shop_category_img  | varchar(2000) | YES  |     | NULL    |                |
| priority           | int(2)        | NO   |     | 0       |                |
| create_time        | datetime      | YES  |     | NULL    |                |
| last_edit_time     | datetime      | YES  |     | NULL    |                |
| parent_id          | int(11)       | YES  | MUL | NULL    |                |
+--------------------+---------------+------+-----+---------+----------------+
     */
    //ID
    private Long shopCategoryId;
    //店铺类别名称
    private String shopCategoryName;
    //店铺类别描述
    private String shopCategoryDesc;
    //店铺类别图片链接地址
    private String shopCategoryImg;
    //店铺类别权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
    //上级ID（实体类）
    private ShopCategory parent;

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public String getShopCategoryDesc() {
        return shopCategoryDesc;
    }

    public void setShopCategoryDesc(String shopCategoryDesc) {
        this.shopCategoryDesc = shopCategoryDesc;
    }

    public String getShopCategoryImg() {
        return shopCategoryImg;
    }

    public void setShopCategoryImg(String shopCategoryImg) {
        this.shopCategoryImg = shopCategoryImg;
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

    public ShopCategory getParent() {
        return parent;
    }

    public void setParent(ShopCategory parent) {
        this.parent = parent;
    }
}
