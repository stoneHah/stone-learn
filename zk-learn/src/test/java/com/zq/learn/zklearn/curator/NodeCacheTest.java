package com.zq.learn.zklearn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : qun.zheng
 * @Description: TODO
 * @date : 2019/7/1 17:09
 **/
public class NodeCacheTest {

    private static final byte[] EMPTY_DATA = new byte[0];

    CuratorFramework client;
    ZKOperations zkOperations;

    @Before
    public void before(){
        client = ZKClientFactory.create("localhost:2181");
        zkOperations = new ZKOperations(client);
    }

    @Test
    public void testNodeCache() throws Exception {
        String path = "/zk-book/nodecache";
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init".getBytes());
        NodeCache nodeCache = new NodeCache(client, path);
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("node data update,new data:" + new String(nodeCache.getCurrentData().getData()));
            }
        });

        client.setData().forPath(path, "u".getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testChildrenCache() throws Exception {
        String path = "/zk-book";
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        cache.getListenable().addListener((client, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("CHILD_ADDED," + event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    System.out.println("CHILD_UPDATED," + event.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    System.out.println("CHILD_REMOVED," + event.getData().getPath());
                    break;
            }
        });
        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
//        Thread.sleep(1000);
        client.delete().forPath(path + "/c1");
        Thread.sleep(5000);
    }

    @After
    public void after(){
        ZKClientFactory.close(client);
    }
}
