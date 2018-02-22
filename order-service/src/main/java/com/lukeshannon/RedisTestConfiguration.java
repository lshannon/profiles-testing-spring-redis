package com.lukeshannon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/*
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-detecting-config
 */
@Configuration
@Profile("test")
public class RedisTestConfiguration {

	private static final Logger log = LoggerFactory.getLogger(RedisTestConfiguration.class);

	@Bean
	RedisConnectionFactory redisConnectionFactory(EmbeddedRedis embeddedRedisServer) {
		log.info("Creating a Redis Connection Factory to embedded Redis");
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(embeddedRedisServer.getRedisPort());
		return connectionFactory;
	}

}
