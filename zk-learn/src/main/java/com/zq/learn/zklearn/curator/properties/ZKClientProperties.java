package com.zq.learn.zklearn.curator.properties;

/**
 * @author : qun.zheng
 * @Description: zookeeper客户端属性配置
 * @date : 2019/6/28 15:56
 **/
public class ZKClientProperties {

    public static final String PREFIX = "supone.zookeeper";
    /**
     * zookeeper服务器的地址
     * ip:port,ip:port
     */
    private String zkServer;

    /**
     * 连接超时时长，单位(ms)
     */
    private int connectionTimeout = 15 * 1000;

    /**
     * session超时时长,单位(ms)
     */
    private int sessionTimeout = 60 * 1000;

    public String getZkServer() {
        return zkServer;
    }

    public void setZkServer(String zkServer) {
        this.zkServer = zkServer;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
