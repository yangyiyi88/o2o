package com.imooc.o2o.entity;

import java.util.Date;

public class Shop {
    /*
    +------------------+---------------+------+-----+---------+----------------+
| Field            | Type          | Null | Key | Default | Extra          |
+------------------+---------------+------+-----+---------+----------------+
| shop_id          | int(10)       | NO   | PRI | NULL    | auto_increment |
| owner_id         | int(10)       | NO   | MUL | NULL    |                |
| area_id          | int(5)        | YES  | MUL | NULL    |                |
| shop_category_id | int(11)       | YES  | MUL | NULL    |                |
| shop_name        | varchar(256)  | NO   |     | NULL    |                |
| shop_desc        | varchar(1024) | YES  |     | NULL    |                |
| shop_addr        | varchar(200)  | YES  |     | NULL    |                |
| phone            | varchar(128)  | YES  |     | NULL    |                |
| shop_img         | varchar(1024) | YES  |     | NULL    |                |
| priority         | int(3)        | YES  |     | 0       |                |
| create_time      | datetime      | YES  |     | NULL    |                |
| last_edit_time   | datetime      | YES  |     | NULL    |                |
| enable_status    | int(2)        | NO   |     | 0       |                |
| advice           | varchar(255)  | YES  |     | NULL    |                |
+------------------+---------------+------+-----+---------+----------------+
     */
    //店铺ID
    private Long shopId;
    //店铺名称
    private String shopName;
    //店铺描述
    private String shopDesc;
    //店铺地址
    private String shopAddr;
    //店铺联系电话
    private String phone;
    //店铺缩略图链接地址
    private String shopImg;
    //店铺权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
    //店铺状态 -1：不可用 0：审核中 1：可用
    private Integer enableStatus;
    //超级管理员给店家的提醒
    private String advice;
    //店铺创建人
    private PersonInfo owner;
    //店铺区域
    private Area area;
    //店铺类别
    private ShopCategory shopCategory;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
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

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Area getArea() {
        return area;
    }

    public PersonInfo getOwner() {
        return owner;
    }

    public void setOwner(PersonInfo owner) {
        this.owner = owner;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }
}
