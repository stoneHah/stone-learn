package com.stone.learn.java.jvm.chapter02;

import java.util.ArrayList;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/12/14
 **/
public class HeapOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        ArrayList<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
