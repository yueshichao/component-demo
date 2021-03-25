package com.demo.lsz;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import static com.demo.lsz.Config.ZK_URL;

@Slf4j
public class TestLock {

    public static void main(String[] args) throws Exception {

        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZK_URL)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("curator_lock")
                .build();
        client.start();

        InterProcessMutex mutex = new InterProcessMutex(client, "/lockNode");
        mutex.acquire();
        try {
            log.info("acquire & do sth.");
            System.out.println("#_#");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }




    }


}
