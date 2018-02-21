package com.lukeshannon;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.embedded.RedisServer;

@Component
public class EmbeddedRedis {
	
	private RedisServer redisServer;
	private static final Logger log = LoggerFactory.getLogger(EmbeddedRedis.class);
	
	@Value("${embedded.redis.port}")
	private Integer redisPort;
	
	public Integer getRedisPort() {
		return redisPort;
	}
	
	public RedisServer getRedisServer() {
		return redisServer;
	}
	
	@PostConstruct
	private void init() {
		try {
			redisServer = new RedisServer(redisPort);
			redisServer.start();
		} catch (IOException e) {
			log.error("Unable to start the embedded Redis server." + e);
		}

	}
	
	
	@PreDestroy
	private void cleanUp() {
		redisServer.stop();
	}


}
