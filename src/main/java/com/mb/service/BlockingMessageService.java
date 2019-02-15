package com.mb.service;

import java.time.Instant;
import java.util.HashMap;

import org.apache.tomcat.jni.Thread;
import org.hibernate.mapping.Map;
import org.springframework.stereotype.Service;

import ratpack.jackson.Jackson;
import ratpack.jackson.JsonRender;

@Service
public class BlockingMessageService {
	
	public String send() {

		return "blocking message service";
	}
	
/*	public JsonRender sendWithoutSleep() {
		System.out.println("######sendWithoutSleep");
		 
		 
		  Map<String, String> map = new HashMap<String, String>();
	        map.put("date2", Instant.now().toString());
	        map.put("message2", "blocking message service");
		 
		return Jackson.json(map);
	}*/
}
