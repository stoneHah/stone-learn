package com.stone.learn.java.function;

import com.sun.tools.javac.comp.Check;

import java.util.function.Predicate;

/**
 * @author qun.zheng
 * @description: TODO
 * @date 2019-06-1122:34
 */
public class Test {
    public static void main(String[] args) {
        Runnable helloWorld = () -> System.out.println("hello world");

//        boolean check = new Test().check(x -> (x > 5));

        SampleData.getThreeArtists().stream().filter(artist -> {
            System.out.println(artist.getName());
            return artist.isFrom("London");
        });

    }

    boolean check(Predicate<Integer> predicate) {
        return false;
    }

    boolean check(IntPred predicate) {
        return false;
    }

    interface IntPred{
        boolean test(Integer value);
    }
}
