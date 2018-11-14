package com.stone.learn.java.concurrent;

import com.stone.learn.java.utils.SleepUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/11/9
 **/
public class CorrectSuspendResume {

    private static final String pattern = "HH:mm:ss";

    public static void main(String[] args) {
        PrintTask printTask = new PrintTask();
        Thread printThread = new Thread(printTask, "PrintThread");
        printThread.setDaemon(true);
        printThread.start();

        SleepUtils.second(3);
        printTask.suspend();
        System.out.println("main thread suspend at " + DateFormatUtils.format(new Date(),pattern));

        SleepUtils.second(3);
        printTask.resume();
        System.out.println("main thread resumed at " + DateFormatUtils.format(new Date(),pattern));

        SleepUtils.second(3);
        printTask.stop();
        System.out.println("main thread stopped at " + DateFormatUtils.format(new Date(),pattern));

        SleepUtils.second(3);
        System.out.println("main thread terminated at " + DateFormatUtils.format(new Date(),pattern));
    }

    private static class PrintTask implements Runnable{

        private Object monitor = new Object();
        private volatile boolean suspend = false;
        private volatile boolean stopped = false;

        @Override
        public void run() {
            while (true) {
                if (stopped) {
                    return;
                }

                if (suspend) {
                    synchronized (monitor) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                System.out.println(Thread.currentThread().getName() + " run at " + DateFormatUtils.format(new Date(),pattern));
                SleepUtils.second(1);
            }
        }

        public void suspend(){
            suspend = true;
        }

        public void resume(){
            synchronized (monitor) {
                monitor.notify();
                suspend = false;
            }
        }

        public void stop() {
            stopped = true;
        }
    }
}
