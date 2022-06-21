package com.lsz.info.push.thread;

import com.lsz.info.push.service.EsService;
import com.lsz.info.push.vo.ServiceMetric;
import io.searchbox.client.JestClient;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Configuration
public class ThreadStarter {

    static final BlockingQueue<List<ServiceMetric>> KAFKA = new ArrayBlockingQueue<>(10000);

    @Resource
    private JestClient jestClient;
    @Resource
    private EsService esService;

    @PostConstruct
    public void start() {
        // 模拟指标收集与上传
        ProducerThread normalService = new ProducerThread(KAFKA);
        ConsumerThread pushToEsService = new ConsumerThread(KAFKA, esService);
        normalService.start();
        pushToEsService.start();
    }


}
