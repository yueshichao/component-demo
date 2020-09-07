package com.demo.lsz;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("zk!");
        Watcher watcher = event -> log.info(event.toString());
        try (ZooKeeper zk = new ZooKeeper("192.168.8.219:2181", 5000, watcher);) {
            log.info(zk.getState().toString());
            List<String> children = zk.getChildren("/", false);
            log.info(children.toString());
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

}
