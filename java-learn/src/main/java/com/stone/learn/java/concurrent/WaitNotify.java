package com.stone.learn.java.concurrent;

import com.stone.learn.java.utils.SleepUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/11/21
 **/
public class WaitNotify {
    private static volatile boolean flag = true;
    private static Object lock = new Object();
    private static String pattern = "HH:mm:ss";

    public static void main(String[] args) {
        new Thread(new WaitTask(),"WaitThread").start();
        SleepUtils.second(1);
        new Thread(new NotifyTask(),"NotifyThread").start();
    }


    static class WaitTask implements Runnable{

        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    System.out.println(Thread.currentThread().getName() + " flag is true,wait at " + DateFormatUtils.format(new Date(),pattern));

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName() + " flag is false,running at " + DateFormatUtils.format(new Date(),pattern));
            }
        }
    }

    static class NotifyTask implements Runnable{

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " hold lock,notify at " + DateFormatUtils.format(new Date(),pattern));
                lock.notify();
                flag = false;

                SleepUtils.second(5);
            }

            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " hold lock again at " + DateFormatUtils.format(new Date(),pattern));
                SleepUtils.second(5);
            }
        }
    }
}
