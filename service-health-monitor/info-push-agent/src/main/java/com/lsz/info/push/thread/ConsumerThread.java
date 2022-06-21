package com.lsz.info.push.thread;

import com.lsz.info.push.service.EsService;
import com.lsz.info.push.vo.ServiceMetric;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class ConsumerThread extends Thread {

    private BlockingQueue<List<ServiceMetric>> mq;
    private EsService esService;

    public ConsumerThread(BlockingQueue<List<ServiceMetric>> mq, EsService esService) {
        this.mq = mq;
        this.esService = esService;
    }

    @Override
    public void run() {
        log.info("消费者线程启动...");
        while (true) {
            try {
                List<ServiceMetric> poll = mq.take();
                push(poll);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void push(List<ServiceMetric> metrics) {
//        log.info("metrics = {}", JSON.toJSONString(metrics));
        esService.push(metrics);
    }


}
