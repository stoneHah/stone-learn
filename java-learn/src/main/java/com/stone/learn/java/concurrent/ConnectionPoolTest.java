package com.stone.learn.java.concurrent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/11/16
 **/
public class ConnectionPoolTest {

    static ConnectionPool pool = new ConnectionPool(10);
    static int threadNum = 20;
    static CountDownLatch startSignal = new CountDownLatch(1);
    static CountDownLatch doneSignal = new CountDownLatch(threadNum);

    /**
     * 构造10个connection
     * 构造20个线程，  每个线程尝试获取20次的连接，
     *      统计每个线程获取到connection的次数和未获取到的次数
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        int count = 20;

        AtomicInteger got = new AtomicInteger(0);
        AtomicInteger notgot = new AtomicInteger(0);

        FetchConnectionTask[] tasks = new FetchConnectionTask[threadNum];
        for (int i = 0; i < threadNum; i++) {
            FetchConnectionTask fetchConnectionTask = new FetchConnectionTask(count, got, notgot);
            Thread thread = new Thread(fetchConnectionTask, "FetchConnectionThread" + i);
            thread.start();

            tasks[i] = fetchConnectionTask;
        }

        startSignal.countDown();
        doneSignal.await();

        System.out.println("total invoke:" + count * threadNum);
        System.out.println("got conection:" + got.get());
        System.out.println("not got conection:" + notgot.get());

    }

    static class FetchConnectionTask implements Runnable{

        private int count;
        private AtomicInteger got;
        private AtomicInteger notgot;

        public FetchConnectionTask(int count, AtomicInteger got, AtomicInteger notgot) {
            this.count = count;
            this.got = got;
            this.notgot = notgot;
        }

        @Override
        public void run() {
            try {
                startSignal.await();

                for (int i = 0; i < count; i++) {
                    Connection conn = pool.fetchConnection(500);
                    if (conn != null) {
                        try {
                            conn.createStatement();
                            conn.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }finally {
                            got.incrementAndGet();
                            pool.releaseConnection(conn);
                        }
                    }else{
                        notgot.incrementAndGet();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
            }
        }


    }
}
