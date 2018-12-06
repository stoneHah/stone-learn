package com.stone.learn.java.lock;

import jdk.internal.org.objectweb.asm.util.TraceAnnotationVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/12/6
 **/
public class FairAndUnfairTest {
    private static final CountDownLatch startSignal = new CountDownLatch(1);
    private static CountDownLatch downDownLatch = new CountDownLatch(1);

    private static ReentrantLock2 unfairLock = new ReentrantLock2();
    private static ReentrantLock2 fairLock = new ReentrantLock2(true);

    public static void main(String[] args) {
//        testLock(fairLock);
        testLock(unfairLock);
    }

    private static void testLock(Lock lock) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Job(lock),String.valueOf(i)).start();
        }
        startSignal.countDown();

        try {
            downDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Job implements Runnable{

        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    System.out.println(String.format("Lock by [%s],Waiting by %s",Thread.currentThread().getName(),
                            getThreadNames(((ReentrantLock2)lock).getQueuedThreads())));
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static List<String> getThreadNames(Collection<Thread> threads){
        return threads.stream().map(t -> t.getName()).collect(Collectors.toList());
    }

    private static class ReentrantLock2 extends ReentrantLock{

        public ReentrantLock2() {
        }

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        protected Collection<Thread> getQueuedThreads() {
            List<Thread> list = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(list);
            return list;
        }
    }
}
