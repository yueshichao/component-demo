package com.lsz.sharding;

import cn.hutool.core.util.RandomUtil;
import com.lsz.shardingjdbc.HorizontalDbMain;
import com.lsz.shardingjdbc.dao.OrderDao;
import com.mysql.jdbc.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HorizontalDbMain.class})
public class DaoTest {

    @Autowired
    OrderDao orderDao;

    @Test
    public void testMultiInsert() {
        ThreadLocalRandom random = RandomUtil.getRandom();
//        long timeStamp = System.currentTimeMillis();
        AtomicLong userId = new AtomicLong(0);
        for (int i = 0; i < 20; i++) {
            double price = random.nextDouble(200);
            int delta = random.nextInt(5);
            orderDao.insertOrder(new BigDecimal(price), userId.getAndAdd(delta), "success");
        }
    }

    @Test
    public void testSelect() {
        List<Long> orderIds = Stream.of(591599429350326273L, 591599430080135168L).collect(Collectors.toList());
        List<Map> maps = orderDao.selectOrderByIds(orderIds);
        System.out.println("maps = " + maps);
    }
}
