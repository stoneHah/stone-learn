package com.stone.learn.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author qun.zheng
 * @description: TODO
 * @date 2019-07-1320:25
 */
public class LockSupportTest {

    private void part() {
        LockSupport.park(this);
    }

    public static void main(String[] args) {
        System.out.println("begin park!");

        new LockSupportTest().part();

        System.out.println("end park!");

    }
}
