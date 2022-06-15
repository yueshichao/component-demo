package com.lsz.info.push;

import cn.hutool.core.thread.ThreadUtil;
import com.lsz.info.push.thread.ConsumerThread;
import com.lsz.info.push.thread.ProducerThread;
import com.lsz.info.push.vo.ServiceMetric;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * 1. docker安装
 * docker run --name es6 -d -p 9200:9200 -p 9300:9300 elasticsearch:6.8.23
 * 2. 修改内存配置
 * vi /etc/sysctl.conf
 * vm.max_map_count=655360 # 添加配置
 * sysctl -p # 重启配置
 *
 *
 * 其他：
 * 各es版本对于type的处理：https://segmentfault.com/a/1190000040330218
 *
 *
 */
@Slf4j
public class PushMain {


    static final BlockingQueue<List<ServiceMetric>> KAFKA = new ArrayBlockingQueue<>(10000);

    public static void main(String[] args) {
        // 模拟指标收集与上传
        ProducerThread normalService = new ProducerThread(KAFKA);
        ConsumerThread pushToEsService = new ConsumerThread(KAFKA);
        normalService.start();
        pushToEsService.start();

        ThreadUtil.sleep(100000000);
    }


}


