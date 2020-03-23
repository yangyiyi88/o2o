package com.imooc.o2o.entity;

import java.util.Date;

public class LocalAuth {
    /*
     * +----------------+--------------+------+-----+---------+----------------+
     * | Field          | Type         | Null | Key | Default | Extra          |
     * +----------------+--------------+------+-----+---------+----------------+
     * | local_auth_id  | int(10)      | NO   | PRI | NULL    | auto_increment |
     * | user_id        | int(10)      | NO   | MUL | NULL    |                |
     * | username       | varchar(128) | NO   |     | NULL    |                |
     * | password       | varchar(128) | NO   |     | NULL    |                |
     * | create_time    | datetime     | YES  |     | NULL    |                |
     * | last_edit_time | datetime     | YES  |     | NULL    |                |
     * +----------------+--------------+------+-----+---------+----------------+
     */
    //ID
    private Long localAuthId;
    //用户名
    private String username;
    //密码
    private String password;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
    //用户实体类
    private PersonInfo personInfo;

    public Long getLocalAuthId() {
        return localAuthId;
    }

    public void setLocalAuthId(Long localAuthId) {
        this.localAuthId = localAuthId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
