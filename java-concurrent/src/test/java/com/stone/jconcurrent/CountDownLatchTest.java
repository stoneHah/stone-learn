package com.stone.jconcurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author : qun.zheng
 * @Description: TODO
 * @date : 2019/7/5 15:48
 **/
@Slf4j
public class CountDownLatchTest {

    private static final CountDownLatch single = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new WaitTask(), "t1").start();
        new Thread(new WaitTask(), "t2").start();


        Thread.sleep(1000);
        single.countDown();

        new Thread(new WaitTask(), "t3").start();

        Thread.sleep(2000);
    }

    private static class WaitTask implements Runnable{

        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + " await...");
            try {
                single.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + " resume...");

        }
    }
}
