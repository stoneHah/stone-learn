package com.zq.learn.zklearn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : qun.zheng
 * @Description: TODO
 * @date : 2019/7/1 17:09
 **/
public class CrudTest {

    private static final byte[] EMPTY_DATA = new byte[0];

    CuratorFramework client;

    @Before
    public void before(){
        client = ZKClientFactory.create("localhost:2181");
    }

    @Test
    public void testCurd() throws Exception {
        client.create().forPath("/head");
        client.delete().inBackground().forPath("/head");
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head/child");
        client.getData().watched().inBackground().forPath("/test");
    }

    @After
    public void after(){
        ZKClientFactory.close(client);
    }
}
