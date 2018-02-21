package com.lukeshannon;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import redis.embedded.RedisServer;

/*
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-detecting-config
 */
@TestConfiguration
public class RedisTestConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(RedisTestConfiguration.class);
	private RedisServer redisServer;

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
	
	@Bean
	RedisConnectionFactory connectionFactory(@Value("${embedded.redis.port}") final int redisPort) {
		return new LettuceConnectionFactory("127.0.0.1", redisPort);
	}


}
