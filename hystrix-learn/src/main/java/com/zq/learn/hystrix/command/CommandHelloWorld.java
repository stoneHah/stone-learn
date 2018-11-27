package com.zq.learn.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.zq.learn.hystrix.utils.SleepUtils;
import rx.Observable;
import rx.Subscriber;

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
        System.out.println("run hystrix command!!");
        return "Hello " + name + "!";
    }

    @Override
    protected String getFallback() {
        return super.getFallback();
    }

    public static void main(String[] args) throws Exception {
        String res = new CommandHelloWorld("Qun").execute();
        System.out.println(res);

        Future<String> future = new CommandHelloWorld("Qun").queue();
        System.out.println(future.get());

        Observable<String> observable = new CommandHelloWorld("Qun").observe();
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("on complete");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("on next:" + s);
            }
        });

        //异步执行
        Observable<String> oc = new CommandHelloWorld("Qun").toObservable();
        oc.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("toObservable on complete");
            }

            @Override
            public void onError(Throwable throwable) {
//                System.out.println("toObservable on complete");
            }

            @Override
            public void onNext(String s) {
                System.out.println("toObservable on next:" + s);
            }
        });

        System.out.println("all command executed!!!");

        SleepUtils.second(1);
    }
}
