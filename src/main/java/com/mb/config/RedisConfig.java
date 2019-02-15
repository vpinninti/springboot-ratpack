package com.mb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
@Configuration
public class RedisConfig {

	@Autowired
	private JedisConnectionFactory jedisConnFactory ;
	
	//@Autowired
	//private LettuceConnectionFactory lettuceConnectionFactory;
	
/*	 @Bean
	  public RedisServer redisServer() {
	    RedisServer.builder().reset();

	    return RedisServer.builder().port(Protocol.DEFAULT_PORT).build();
	  } 
	 
	@Bean
	public RedisServerConfiguration redisServerConfiguration(){
	    return new RedisServerConfiguration();
	}*/
	
	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(jedisConnFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		return redisTemplate;
	}
}
