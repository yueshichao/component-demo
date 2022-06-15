package com.lsz.info.push.thread;

import cn.hutool.core.thread.ThreadUtil;
import com.google.common.collect.Lists;
import com.lsz.info.push.vo.ServiceMetric;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@NoArgsConstructor
public class ProducerThread extends Thread {

    private BlockingQueue<List<ServiceMetric>> mq;

    public ProducerThread(BlockingQueue<List<ServiceMetric>> mq) {
        this.mq = mq;
    }

    @Override
    public void run() {
        while (true) {
            ThreadUtil.sleep(500);
            ServiceMetric serviceMetric = ServiceMetric.defaultMetric();
            mq.offer(Lists.newArrayList(serviceMetric));
        }
    }
}
