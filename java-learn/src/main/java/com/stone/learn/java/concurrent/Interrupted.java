package com.stone.learn.java.concurrent;

import com.stone.learn.java.utils.SleepUtils;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断
 *
 * @author qun.zheng
 * @create 2018/11/9
 **/
public class Interrupted {
    public static void main(String[] args) {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        sleepThread.start();

        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        busyThread.start();

        //线程充分运行
        SleepUtils.second(5);
        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread的中断状态:" + sleepThread.isInterrupted());
        System.out.println("BusyThread的中断状态:" + busyThread.isInterrupted());
    }

    static class SleepRunner implements Runnable{

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    static class BusyRunner implements Runnable{

        @Override
        public void run() {
            Thread.currentThread().isInterrupted();
                while (true) {
                }

        }
    }
}
