package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LocalAuthDaoTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    public void testQueryLocalByUserNameAndPwd() {
        LocalAuth auth = localAuthDao.queryLocalByUserNameAndPwd("testbind", "123456");
        assertEquals(auth.getPersonInfo().getName(), "测试");
    }

    @Test
    public void testQueryLocalByUserId() {
        LocalAuth auth = localAuthDao.queryLocalByUserId(1l);
        assertEquals(auth.getPersonInfo().getName(), "测试");
    }

    @Test
    public void testInsertLocalAuth() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(8l);
        LocalAuth localAuth = new LocalAuth();
        localAuth.setPersonInfo(personInfo);
        localAuth.setUsername("lx");
        localAuth.setPassword("123");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testUpdateLocalAuth() {
        int effectedNum = localAuthDao.updateLocalAuth(8l, "lx", "456", "789", new Date());
        assertEquals(1, effectedNum);
    }
}
