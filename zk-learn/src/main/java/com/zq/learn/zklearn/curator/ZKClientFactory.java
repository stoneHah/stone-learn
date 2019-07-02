package com.zq.learn.zklearn.curator;

import com.zq.learn.zklearn.curator.properties.ZKClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author : qun.zheng
 * @Description: zookeeper客户端工厂，基于curator实现
 * @date : 2019/6/28 15:53
 **/
@Slf4j
public class ZKClientFactory {

    private static final ConcurrentHashMap<String,CuratorFramework> cache = new ConcurrentHashMap<>(5);



    /**
     * 创建zk的客户端
     * @param connectionString
     * @return
     */
    public static CuratorFramework create(String connectionString){
        ZKClientProperties properties = new ZKClientProperties();
        properties.setZkServer(connectionString);

        return create(properties);
    }

    /**
     * 创建zk的客户端
     * @param properties
     * @return
     */
    public static CuratorFramework  create(ZKClientProperties properties){
        String serverKey = serverKey(properties.getZkServer());

        if (!StringUtils.hasText(serverKey)) {
            log.warn("create curator client failed on zookeeper server not configed");
            throw new IllegalArgumentException("zookeeper server not configed");
        }

        CuratorFramework client = cache.get(serverKey);

        if(client == null){
            // these are reasonable arguments for the ExponentialBackoffRetry. The first
            // retry will wait 1 second - the second will wait up to 2 seconds - the
            // third will wait up to 4 seconds.
            ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

            // using the CuratorFrameworkFactory.builder() gives fine grained control
            // over creation options. See the CuratorFrameworkFactory.Builder javadoc
            // details
            client = CuratorFrameworkFactory.builder()
                    .connectString(properties.getZkServer())
                    .retryPolicy(retryPolicy)
                    .connectionTimeoutMs(properties.getConnectionTimeout())
                    .sessionTimeoutMs(properties.getSessionTimeout())
                    // etc. etc.
                    .build();
            CuratorFramework prevClient = cache.putIfAbsent(serverKey, client);
            if (prevClient == null) {
                client.start();
            }else{
                log.info("get zk client from cache:{}",properties.getZkServer());
                client = prevClient;
            }
        }else{
            log.info("get zk client from cache:{}",properties.getZkServer());
//            client.isStarted()
        }

        return client;
    }

    /**
     * TODO 按照某种规则进行排序
     * @param serverStr
     * @return
     */
    private static String serverKey(String serverStr) {
        return serverStr;
    }

    /**
     * 关闭zk客户端
     * @param client
     */
    public static void close(CuratorFramework client){
        String serverKey = client.getZookeeperClient().getCurrentConnectionString();
        CuratorFramework prev = cache.remove(serverKey);
        if (prev == null) {
            log.warn("client not managed by cache [server={}]",serverKey);
        }
        CloseableUtils.closeQuietly(client);
    }

    public static void main(String[] args) throws InterruptedException {
        String zkServer = "localhost:2181";
        CuratorFramework client = null;
        try {
            client = ZKClientFactory.create(zkServer);

            TimeUnit.SECONDS.sleep(1);

            ZKClientFactory.create(zkServer);
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }

}
