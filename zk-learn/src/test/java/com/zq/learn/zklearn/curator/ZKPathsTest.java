package com.zq.learn.zklearn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author : qun.zheng
 * @Description: TODO
 * @date : 2019/7/1 17:09
 **/
public class ZKPathsTest {

    private static final byte[] EMPTY_DATA = new byte[0];

    static CuratorFramework client;
    static ZKOperations zkOperations;

    public static void main(String[] args) throws Exception {
        client = ZKClientFactory.create("localhost:2181");
        zkOperations = new ZKOperations(client);

        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();

        String path = "/curator_zkpath_sample";

        System.out.println(ZKPaths.fixForNamespace("sub", path));
        System.out.println(ZKPaths.makePath(path, "sub"));
        System.out.println(ZKPaths.getNodeFromPath("/curator_zkpath_sample/sub1"));
        ZKPaths.PathAndNode pathAndNode = ZKPaths.getPathAndNode("/curator_zkpath_sample/sub1");
        System.out.println(pathAndNode.getPath());
        System.out.println(pathAndNode.getNode());

        String dir1 = path + "/child1";
        String dir2 = path + "/child2";
        ZKPaths.mkdirs(zooKeeper, dir1);
        ZKPaths.mkdirs(zooKeeper, dir2);
        System.out.println(ZKPaths.getSortedChildren(zooKeeper, path));

        ZKPaths.deleteChildren(zooKeeper, path, true);

        ZKClientFactory.close(client);
    }

}
