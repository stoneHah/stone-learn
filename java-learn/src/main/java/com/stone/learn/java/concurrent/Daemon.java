package com.stone.learn.java.concurrent;

import com.stone.learn.java.utils.SleepUtils;

/**
 * 创建后台线程
 *
 * @author qun.zheng
 * @create 2018/10/25
 **/
public class Daemon {

    public static void main(String[] args) {
        Thread daemonThread = new Thread(new DaemonThread(), "DaemonThread");
        daemonThread.setDaemon(true);
        daemonThread.start();

        SleepUtils.second(5);
    }

    private static class DaemonThread implements Runnable{

        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                System.out.println("DaemonThread is terminated");
            }
        }
    }
}
