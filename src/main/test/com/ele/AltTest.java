package com.ele;

import com.eleme.ElemeApplication;
import com.eleme.dao.AltDao;
import com.eleme.entity.Alt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElemeApplication.class)
public class AltTest {

    @Resource
    AltDao altDao;

    @Test
    public void test1() {
        List<Alt> numCookie = altDao.getNumCookie(5);
        Integer integer = altDao.updateBatchUseNum(numCookie);
        System.out.println(integer);
    }
}
