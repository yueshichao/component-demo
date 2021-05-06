package com.lsz.redis.lua.limiter;

import cn.hutool.core.thread.ThreadUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@NoArgsConstructor
public class SingleRL implements RL {

    private int rate;

    private double num;

    private long last = 0L;


    @Override
    public synchronized boolean tryAcquire() {
        long cur = System.currentTimeMillis();
        if (last == 0 || num > 0) {
            last = cur;
            num--;
            return true;
        }
        double delta = cur - last;
        last = cur;
        log.debug("delta = {}", delta);
        if (delta > 1000) {
            num = rate;
            num--;
            return true;
        } else {
            double product = delta / 1000 * rate;
            if (product >= 1) {
                return true;
            } else {
                num += product;
            }
            log.debug("product = {}, num = {}", product, num);
        }
        return false;
    }

    public static void main(String[] args) {
        SingleRL singleRL = new SingleRL();
        singleRL.setNum(1);
        singleRL.setRate(1);

        while (true) {
            boolean b = singleRL.tryAcquire();
            log.info("b = {}, num = {}", b, singleRL.getNum());
            ThreadUtil.sleep(500);
        }


    }

}
