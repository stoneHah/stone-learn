package com.stone.learn.java.concurrent;

import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/11/21
 **/
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initPoolSize) {
        for (int i = 0; i < initPoolSize; i++) {
            pool.add(ConnectionDriver.createConnection());
        }
    }

    public Connection fetchConnection(long mill) throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        synchronized (pool) {
            while (pool.isEmpty()){
                if (mill <= 0) {
                    pool.wait();
                }else{
                    long delay = mill - now;
                    /*if (delay > 0) {
                        pool.wait(delay);
                        now = System.currentTimeMillis() - base;
                    }*/
                    if (delay <= 0) {
                        break;
                    }else{
                        pool.wait(delay);
                        now = System.currentTimeMillis() - base;
                    }
                }
            }

            Connection conn = null;
            if (!pool.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " get connection");
                conn = pool.removeFirst();
            }

            return conn;
        }
    }

    public void releaseConnection(Connection connection){
        if(connection != null){
            System.out.println(Thread.currentThread().getName() + " releaseConnection");
            synchronized (pool) {
                pool.addLast(connection);
                pool.notify();
            }
        }
    }
}
