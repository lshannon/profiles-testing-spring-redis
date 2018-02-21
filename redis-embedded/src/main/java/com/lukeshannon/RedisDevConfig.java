package com.lukeshannon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Profile("dev")
public class RedisDevConfig {

	@Bean
	RedisConnectionFactory connectionFactory(@Value("${installed.redis.port}") final int redisPort) {
		return new LettuceConnectionFactory("127.0.0.1", redisPort);
	}

}
