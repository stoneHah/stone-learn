package com.stone.learn.java.lock;

import com.stone.learn.java.utils.SleepUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/12/6
 **/
public class TwinceLockTest {
    private static final TwinceLock LOCK = new TwinceLock();
    private static CountDownLatch startDownLatch = new CountDownLatch(1);
    private static CountDownLatch downDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        for (int i = 0;i < 10;i++) {
            new Thread(new Worker(),"Worker" + i).start();
        }

        startDownLatch.countDown();
        downDownLatch.await();
    }

    private static class Worker implements Runnable{

        @Override
        public void run() {
            try {
                startDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (true) {
                LOCK.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " get lock at " + DateFormatUtils.format(new Date(),"HH:mm:ss"));
                    SleepUtils.second(1);
                }finally {
                    LOCK.unlock();
                }
            }
        }
    }
}
