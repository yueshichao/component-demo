package com.demo.lsz;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Data
@Slf4j
public class MyZkLock {

    private String parentPath;

    private String currentPath;

    private ZooKeeper zk;

    private String nodeName;


    public MyZkLock(String parentPath, String nodeName, ZooKeeper zk) {
        this.parentPath = parentPath;
        this.nodeName = nodeName;
        this.zk = zk;

    }

    public void lock() {
        try {
            // 在/lock节点下，创建临时序号节点/lsz
            currentPath = zk.create(parentPath + "/" +nodeName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            String currentNode = currentPath.replace(parentPath + "/", "");
            log.info("lock path = {}, currentNode = {}", currentPath, currentNode);
            List<String> children = zk.getChildren(parentPath, false);
            log.info("lock seq = {}", children);
            Collections.sort(children);
            log.info("排序 = {}", children);
            String preNode = null;
            for (String child : children) {
                if (currentNode.equals(child)) {
                    break;
                }
                preNode = child;
            }

            if (StrUtil.isEmpty(preNode)) {
                return;
            }
            log.info("wait previous node = {}", preNode);

            CountDownLatch latch = new CountDownLatch(1);
            String prePath = parentPath + "/" + preNode;
            zk.getData(prePath, event -> {
                log.info("{} changed = {}", prePath, event);
                // 前一个节点被删除，表示释放锁
                if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
                    latch.countDown();
                }
            }, new Stat());
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            if (e instanceof KeeperException.NoNodeException) {
                log.info("等待的node已被删除，可直接获取锁");
                return;
            }
        }
    }

    public void unlock() {
        try {
            log.info("删除currentPath = {}", currentPath);
            zk.delete(currentPath, -1);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
