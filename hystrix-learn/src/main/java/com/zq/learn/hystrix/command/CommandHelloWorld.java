package com.zq.learn.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/7/4
 **/
public class CommandHelloWorld extends HystrixCommand<String> {
    private String name;

    protected CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + "!";
    }

    public static void main(String[] args) throws Exception {
        String res = new CommandHelloWorld("Qun").execute();
        System.out.println(res);

        Future<String> future = new CommandHelloWorld("Qun").queue();
        System.out.println(future.get());

        Observable<String> observable = new CommandHelloWorld("Qun").observe();
//        observable.
    }
}
