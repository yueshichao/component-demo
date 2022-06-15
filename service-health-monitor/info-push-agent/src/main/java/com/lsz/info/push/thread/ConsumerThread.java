package com.lsz.info.push.thread;

import com.alibaba.fastjson.JSON;
import com.lsz.info.push.util.JestUtil;
import com.lsz.info.push.vo.ServiceMetric;
import io.searchbox.client.JestClient;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class ConsumerThread extends Thread {

    private BlockingQueue<List<ServiceMetric>> mq;
    private JestClient jestClient;

    public ConsumerThread(BlockingQueue<List<ServiceMetric>> mq) {
        this.mq = mq;
        this.jestClient = JestUtil.getJestClient();
    }

    @Override
    public void run() {
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
        log.info("metrics = {}", JSON.toJSONString(metrics));

    }


}
