package com.stone.learn.java.utils;

import java.util.concurrent.TimeUnit;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/10/25
 **/
public class SleepUtils {
    public static void second(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }
}
