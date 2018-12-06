package com.stone.learn.java.lock;

import com.stone.learn.java.utils.SleepUtils;
import org.springframework.util.StringUtils;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/12/5
 **/
public class MutexTest {

    private static Mutex lock = new Mutex();
    private static CountDownLatch doneSignal = new CountDownLatch(1);

    public static void main(String[] args) {
        new Thread(new TryLockTask(),"LockTaskThread").start();

        SleepUtils.second(1);

        new Thread(new TryLockTask(),"TryLockThread1").start();

    }

    private static class TryLockTask implements Runnable{

        @Override
        public void run() {
            lock.lock();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String  command = scanner.nextLine();
                if (StringUtils.hasText(command)) {
                    switch (command) {
                        case "unlock":
                            lock.unlock();
                            break;
                        case "new thread":
                            break;
                    }

                }
            }

            try {
                doneSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
