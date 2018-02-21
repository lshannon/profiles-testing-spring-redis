package com.lukeshannon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class RedisEmbeddedApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisEmbeddedApplication.class, args);
	}
}
