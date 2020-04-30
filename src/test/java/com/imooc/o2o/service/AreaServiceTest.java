package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest{

    @Resource
    private AreaService areaService;
    @Test
    public void testAreaService(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("西苑",areaList.get(0).getAreaName()
        );
    }

}
