package com.lsz.shardingjdbc.dao;


import com.lsz.shardingjdbc.ReadWriteSeparateMain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ReadWriteSeparateMain.class})
@Slf4j
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    public void testReadWriteDao() {
        log.info("test read write...");

        userDao.insertUser(0L, "io");
        userDao.insertUser(1L, "slack");

        List<Long> ids = Stream.of(0L, 1L).collect(Collectors.toList());

        List<Map> maps = userDao.selectUserByIds(ids);
        log.info(maps.toString());


    }

}
