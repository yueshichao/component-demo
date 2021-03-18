package com.demo.lsz;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Main {

    public static final String URL = "192.168.159.129:2181";

    public static void main(String[] args) throws Exception {

        // 连接
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(URL)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("base")
                .build();
        client.start();

        // 创建节点
        client.create().creatingParentContainersIfNeeded()  // 递归创建父节点
                .withMode(CreateMode.PERSISTENT)    // 持久型节点
                .forPath("/nodeA", "init".getBytes());

        // 获取节点数据
        byte[] bytes = client.getData().forPath("/nodeA");
        log.info("{}", new String(bytes));

        // 查询节点状态
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/nodeA");
        log.info("{}", stat);

        // 删除节点
        client.delete()
                .guaranteed()
                .deletingChildrenIfNeeded()
                .withVersion(stat.getVersion())
                .forPath("/nodeA");


        // 重新创建一个A方便下面操作
        client.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/nodeA", "init".getBytes());
        // 事务
        client.inTransaction().check().forPath("/nodeA")
                .and()
                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeB", "init".getBytes())
                .and()
                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeC", "init".getBytes())
                .and()
                .commit();

        // 创建节点为查询做准备
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/nodeA/node11/node12");
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/nodeA/node21/node22");

        // 查询节点是否存在
        Stat nodeA = client.checkExists().forPath("/nodeA");
        log.info("{}", nodeA);// 如果不存在返回null

        // 查询子节点路径
        List<String> list = client.getChildren().forPath("/nodeA");
        log.info("{}", list);// [node21, node11]

        // 异步创建节点
        ExecutorService executor = Executors.newFixedThreadPool(1);
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((curatorFramework, curatorEvent) -> {
                    log.info("eventType = {}, resultCode = {}", curatorEvent.getType(), curatorEvent.getResultCode());
                }, executor)
                .forPath("/nodeD");
        executor.shutdown();


    }


}
