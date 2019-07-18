package com.stone.learn.concurrent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author qun.zheng
 * @description: TODO
 * @date 2019-07-0822:39
 */
public class RandomTest {
    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }
}
