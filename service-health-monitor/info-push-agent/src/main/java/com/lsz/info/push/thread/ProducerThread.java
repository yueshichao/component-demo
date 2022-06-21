package com.lsz.info.push.thread;

import cn.hutool.core.thread.ThreadUtil;
import com.google.common.collect.Lists;
import com.lsz.info.push.vo.ServiceMetric;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@NoArgsConstructor
@Slf4j
public class ProducerThread extends Thread {

    private BlockingQueue<List<ServiceMetric>> mq;

    public ProducerThread(BlockingQueue<List<ServiceMetric>> mq) {
        this.mq = mq;
    }

    @Override
    public void run() {
        log.info("生产者线程启动...");
        while (true) {
            ThreadUtil.sleep(1000);
            ServiceMetric serviceMetric = ServiceMetric.defaultMetric();
            mq.offer(Lists.newArrayList(serviceMetric));
        }
    }
}
