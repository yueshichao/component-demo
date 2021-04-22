package com.lsz.shardingjdbc;


import com.lsz.shardingjdbc.dao.DictDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CommonTableMain.class})
@Slf4j
public class TestDao {

    @Autowired
    DictDao dictDao;


    @Test
    public void testInsert() {

        log.info("test case");
        dictDao.insertDict(1L, "user_type", "0", "admin");
        dictDao.insertDict(2L, "user_type", "1", "normal");
        dictDao.insertDict(3L, "user_type", "2", "illegal");

    }


    @Test
    public void testDelete() {

        log.info("test case");

        dictDao.delete(2L);
    }

}
