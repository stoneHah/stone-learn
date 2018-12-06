package com.stone.learn.java.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/12/6
 **/
public class TwinceLock implements Lock {

    private Sync lock = new Sync(3);

    @Override
    public void lock() {
        lock.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return lock.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return lock.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        lock.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return lock.newCondition();
    }

    private static class Sync extends AbstractQueuedSynchronizer{
        protected Sync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (;;) {
                int current = getState();
                int newCount = current - reduceCount;
                if(newCount < 0 || compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (;;) {
                int current = getState();
                int newCount = current + returnCount;
                if(compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }
}
