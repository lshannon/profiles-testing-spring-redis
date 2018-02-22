package com.lukeshannon;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import redis.embedded.RedisServer;

@Component
public class EmbeddedRedis {
	
	private RedisServer redisServer;
	private static final Logger log = LoggerFactory.getLogger(EmbeddedRedis.class);
	
	@Autowired
	private Environment environment;
	
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
		if (checkIfTest()) {
			try {
				log.info("Intializing The Embedded Redis Server");
				redisServer = new RedisServer(redisPort);
				redisServer.start();
				log.info("Embedded Redis Server Started");
			} catch (IOException e) {
				log.error("Unable to start the embedded Redis server." + e);
			}
		}
		
	}
	
	@PreDestroy
	private void cleanUp() {
		if (checkIfTest()) {
			log.info("Shutting Down The Embedded Redis Server");
			redisServer.stop();
		}
	}
	
	private boolean checkIfTest() {
		return Arrays.asList(environment.getActiveProfiles()).contains("test");
	}

}
