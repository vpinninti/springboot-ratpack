package com.mb.service;

public interface CacheService {
	
	public void remove(String key);
	public Object get(String key);
	public void put(String key,Object value);
	public String getToken( );

}
