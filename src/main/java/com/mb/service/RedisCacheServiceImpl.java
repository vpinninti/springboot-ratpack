package com.mb.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("redisCacheService")
public class RedisCacheServiceImpl implements CacheService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	

	@Override
	public Object get(String key) {

		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void put(String key, Object value) {
		
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key,600, TimeUnit.SECONDS);

	}

	@Override
	public String getToken() {
		UUID id = UUID.randomUUID();
		return String.valueOf(id);
	}
	
	@Override
	public void remove(String key) {

	}

}
