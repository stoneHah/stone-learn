package com.stone.learn.java.function;

import com.sun.tools.javac.comp.Check;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<String> list = Stream.of("a", "b", "hello").map(e -> e.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(list);

        Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                .flatMap(numbers -> numbers.stream())
                .min(Comparator.comparing(n -> n)).get();
//                .collect(Collectors.toList());

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
