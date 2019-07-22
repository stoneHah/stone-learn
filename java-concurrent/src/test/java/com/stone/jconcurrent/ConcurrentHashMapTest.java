package com.stone.jconcurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : qun.zheng
 * @Description: TODO
 * @date : 2019/7/22 14:11
 **/
public class ConcurrentHashMapTest {

    @Test
    public void testPutIfAbsent(){
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(12);
        Object prev = map.putIfAbsent("hello", "world");
        assert prev == null;

        prev = map.putIfAbsent("hello", "shenxx");
        assert prev != null;

        assert "world".equals(map.get("hello"));
    }
}
