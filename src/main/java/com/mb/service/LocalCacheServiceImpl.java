package com.mb.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service("localCacheService")
public class LocalCacheServiceImpl  implements CacheService{

	private static Map<String,Object> localMap = new ConcurrentHashMap<String,Object>();
	
	@Override
	public void remove(String key) {
		if(localMap.containsKey(key))
			localMap.remove(key);
	}

	@Override
	public Object get(String key) {
	
		if(localMap.containsKey(key))
			return localMap.get(key);
		
		return null;
	}

	@Override
	public void put(String key, Object value) {
		localMap.put(key, value);
	}
	
	@Override
	public String getToken( ) {
		UUID id = UUID.randomUUID();
		return String.valueOf(id);
	}

}
