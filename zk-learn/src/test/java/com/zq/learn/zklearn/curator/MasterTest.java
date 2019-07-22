package com.zq.learn.zklearn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : qun.zheng
 * @Description: TODO
 * @date : 2019/7/1 17:09
 **/
public class MasterTest {

    private static final byte[] EMPTY_DATA = new byte[0];

    static CuratorFramework client;
    static ZKOperations zkOperations;

    public static void main(String[] args) throws InterruptedException {
        client = ZKClientFactory.create("localhost:2181");
        zkOperations = new ZKOperations(client);

        String master_path = "/curator_recipes_master_path";
        LeaderSelector selector = new LeaderSelector(client, master_path, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为master角色");
                Thread.sleep(3000);
                System.out.println("完成master操作，释放master权利");
            }
        });

        selector.autoRequeue();
        selector.start();

        Thread.sleep(Integer.MAX_VALUE);

        ZKClientFactory.close(client);
    }

}
