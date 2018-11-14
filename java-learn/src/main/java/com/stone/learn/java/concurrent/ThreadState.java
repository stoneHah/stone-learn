package com.stone.learn.java.concurrent;

import com.stone.learn.java.utils.SleepUtils;

import java.util.Arrays;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/10/25
 **/
public class ThreadState {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(Thread.State.values()));

        new Thread(new TimeWaiting(),"TimeWaitingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        new Thread(new Blocked(),"BlockedThread1").start();
        new Thread(new Blocked(),"BlockedThread2").start();
    }

    private static class TimeWaiting implements Runnable{

        @Override
        public void run() {
            while (true) {
                SleepUtils.second(1);
            }
        }
    }

    private static class Waiting implements Runnable{

        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class Blocked implements Runnable{

        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(1);
                }
            }
        }
    }
}
