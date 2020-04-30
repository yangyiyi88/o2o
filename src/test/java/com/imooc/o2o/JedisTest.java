package com.imooc.o2o;

import com.imooc.o2o.cache.JedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class JedisTest extends BaseTest {
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Test
    public void TestJedist() {
        String status = jedisStrings.set("name", "yk");
        System.out.println(status);
        Set<String> keys = jedisKeys.keys("*");
        System.out.println(keys);
    }
}
