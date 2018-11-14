package com.stone.learn.java.concurrent;

import com.stone.learn.java.utils.SleepUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * 废弃的
 *  suspend 调用此方法不会释放占有的资源(锁)
 *  resume 跟suspend是一对的
 *  stop   不会保证线程的资源正常释放，通常是没有给予线程完成资源释放工作的机会
 *
 * @author qun.zheng
 * @create 2018/11/9
 **/
public class Deprecated {
    private static final String pattern = "HH:mm:ss";

    public static void main(String[] args) {
        Thread printThread = new Thread(new PrintThread(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();

        SleepUtils.second(3);
        printThread.suspend();
        System.out.println("main thread suspend at " + DateFormatUtils.format(new Date(),pattern));

        SleepUtils.second(3);
        printThread.resume();
        System.out.println("main thread resumed at " + DateFormatUtils.format(new Date(),pattern));

        SleepUtils.second(3);
        printThread.stop();
        System.out.println("main thread stopped at " + DateFormatUtils.format(new Date(),pattern));

        SleepUtils.second(3);
        System.out.println("main thread terminated at " + DateFormatUtils.format(new Date(),pattern));
    }

    private static class PrintThread implements Runnable{

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " run at " + DateFormatUtils.format(new Date(),pattern));
                SleepUtils.second(1);
            }
        }
    }
}
