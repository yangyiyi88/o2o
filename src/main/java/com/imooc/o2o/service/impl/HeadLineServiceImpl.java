package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exception.HeadLineOperationException;
import com.imooc.o2o.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("headLineService")
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    private Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        //定义redis的key前缀
        String key = HLLISTKEY;
        //定义接收对象
        List<HeadLine> headLineList = null;
        //定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        //拼接出redis的key
        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        //判断key是否存在
        if (!jedisKeys.exists(key)) {
            //若不存在，则从数据库里面取出相应数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            //将相关的实体类集合转换成string，存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            //若存在，则直接从redis里面取出相应数据
            String jsonString = jedisStrings.get(key);
            //指定要将string转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                //将相关key对应的value里的string转换成对象的实体类集合
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }
}
