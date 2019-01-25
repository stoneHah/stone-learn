package com.stone.learn.redislearn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLearnApplicationTests {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testStringStruct(){
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		ops.set("status","7");
		System.out.println(ops.get("status"));

		ops.setBit("bitstatus",1,true);
		System.out.println(ops.getBit("bitstatus",1));
	}

}

