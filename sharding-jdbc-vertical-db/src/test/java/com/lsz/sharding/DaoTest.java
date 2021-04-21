package com.lsz.sharding;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.lsz.shardingjdbc.VerticalDBMain;
import com.lsz.shardingjdbc.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VerticalDBMain.class})
@Slf4j
public class DaoTest {

    @Autowired
    UserDao userDao;

    @Test
    public void test() {
        log.info("test case...");
    }


    @Test
    public void testInsertUser() {
        log.info("test case...");
        Snowflake snowflake = IdUtil.createSnowflake(0, 0);

        for (int i = 0; i < 20; i++) {
            long userId = snowflake.nextId();
            userDao.insertUser(userId, "name" + i);
        }

    }

}
