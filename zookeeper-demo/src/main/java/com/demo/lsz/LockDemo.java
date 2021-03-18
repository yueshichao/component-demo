package com.demo.lsz;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/*
* 参考：
* https://juejin.im/post/6844903921564008461
* https://blog.csdn.net/crazymakercircle/article/details/85956246
*
* */

@Slf4j
public class LockDemo {

    public static final String URL = "192.168.159.129:2181";
    public static final int SESSION_TIMEOUT = 5000;
    private static ZooKeeper zk;
    // 所有锁节点的父节点，在运行前先create一下这个节点
    private static final String parentPath = "/lock";

    public static void main(String[] args) {
        // 连接zk
        CountDownLatch connectedLatch = new CountDownLatch(1);
        try {
            zk = new ZooKeeper(URL, SESSION_TIMEOUT, (event) -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    log.info("连接成功~");
                    connectedLatch.countDown();
                }
            });
            connectedLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                MyZkLock lock = new MyZkLock(parentPath, "lsz", zk);
                lock.lock();
                // 睡眠一段时间
                int sleepTime = 500 + random.nextInt(500);
                log.info("do sth，time = {}", sleepTime);
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }, "Thread-" + i).start();
        }

        LockSupport.park();

    }

}
