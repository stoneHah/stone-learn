package com.zq.learn.zklearn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : qun.zheng
 * @Description: TODO
 * @date : 2019/7/1 17:09
 **/
public class CrudTest {

    private static final byte[] EMPTY_DATA = new byte[0];

    CuratorFramework client;
    ZKOperations zkOperations;

    @Before
    public void before(){
        client = ZKClientFactory.create("localhost:2181");
        zkOperations = new ZKOperations(client);
    }

    @Test
    public void testCurd() throws Exception {
        client.create().forPath("/head");
        client.delete().inBackground().forPath("/head");
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head/child");
        client.getData().watched().inBackground().forPath("/test");
    }

    @Test
    public void testDelData() throws Exception {
        String path = "/zk-book/c1";
        zkOperations.createEphemeral(path,"init".getBytes());

        Stat stat = new Stat();
        zkOperations.getDataWithStat(path,stat);

        zkOperations.delete(path,stat.getVersion());
    }

    @Test
    public void testCreateAsync() throws Exception{
        String path = "/zk-book/stone";
        CountDownLatch semaphore = new CountDownLatch(2);
        ExecutorService exec = Executors.newFixedThreadPool(2);

        System.out.println("Main thread:" + Thread.currentThread().getName());

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground((client, event) -> {
            System.out.println("Event[code:"+event.getResultCode()+",type:"+event.getType()+"]");
            System.out.println("thread of processResult:" + Thread.currentThread().getName());

            semaphore.countDown();
        }, exec).forPath(path);

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground((client, event) -> {
            System.out.println("Event[code:"+event.getResultCode()+",type:"+event.getType()+"]");
            System.out.println("thread of processResult:" + Thread.currentThread().getName());

            semaphore.countDown();
        }).forPath(path);

        semaphore.await();
        exec.shutdown();
    }

    @After
    public void after(){
        ZKClientFactory.close(client);
    }
}
