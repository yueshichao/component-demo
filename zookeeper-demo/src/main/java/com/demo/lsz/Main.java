package com.demo.lsz;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Main {



    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        log.info("zookeeper demo!");
        // 1. 连接，需要一定时间，连接成功会通知Watch，如果没有连接成功，是无法进行其他操作的
        // 连接成功时放锁
        CountDownLatch connectLock = new CountDownLatch(1);
        Watcher connectWatch = (event) -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                log.info("连接成功~");
                connectLock.countDown();
            }
        };
        // 创建连接
        ZooKeeper zk = new ZooKeeper("192.168.8.219:2181", 5000, connectWatch);
        log.info(zk.getState().toString());
        // 等待连接完成
        connectLock.await();

        // 2. 创建一个ZNode，节点路径，节点中存放字符串，权限设置，创建节点具体类型
        String path = zk.create("/lsz", "lsz write data...".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        log.info("create path = {}", path);

        // 3. exist
        Stat exists = zk.exists(path, false);
        log.info("exists = {}", exists);

        // 4. 设置ZNode内容
        Stat stat = zk.setData(path, "change data".getBytes(), -1);
        log.info("set data = {}", stat);

        // 5. 获取内容
        byte[] data = zk.getData(path, false, new Stat());
        log.info("get data = {}", new String(data));

        // 6. 删除节点，version为-1表示删除所有
        zk.delete(path, -1);

        zk.close();
    }

}
