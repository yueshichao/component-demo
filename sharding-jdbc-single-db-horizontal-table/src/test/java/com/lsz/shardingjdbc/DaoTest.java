package com.lsz.shardingjdbc;


import cn.hutool.core.util.RandomUtil;
import com.lsz.shardingjdbc.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJdbcMain.class})
public class DaoTest {

    @Test
    public void test() {
        log.info("test case ...");
    }

    @Autowired
    OrderDao orderDao;

    @Test
    public void testInsert() {
        int success = orderDao.insertOrder(new BigDecimal(6.5), 11L, "success");
    }


    @Test
    public void testMultiInsert() {
        ThreadLocalRandom random = RandomUtil.getRandom();
        for (int i = 0; i < 20; i++) {
            double price = random.nextDouble(200);
            long userId = random.nextLong(3000);
            orderDao.insertOrder(new BigDecimal(price), userId, "success");
        }
    }



}
