package com.stone.learn.java.concurrent;

import com.stone.learn.java.utils.SleepUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/11/21
 **/
public class ConnectionDriver {

    static class ConnectionHandler implements InvocationHandler{

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().equals("commit")){
                SleepUtils.millSecond(500);
            }
            return null;
        }
    }
    public static Connection createConnection(){
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),new Class[]{Connection.class},
                new ConnectionHandler());
    }
}
