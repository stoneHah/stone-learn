package com.stone.learn.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * @author qun.zheng
 * @description: TODO
 * @date 2019-07-1707:14
 */
public class ReadWriteLockTest {

    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    public static void main(String[] args) {
        ReadLock readLock = lock.readLock();
        readLock.lock();
        try {
            System.out.println("get read lock");

            foo();
        } finally {
            readLock.unlock();
        }
    }

    private static void foo() {
        System.out.println("try to get write lock");
        WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            System.out.println("hello foo");
        } finally {
            writeLock.unlock();
        }
    }
}
