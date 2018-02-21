package com.lukeshannon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.common.RedisServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@Profile("cloud")
public class RedisCloudConfig {
	
	@Value("${redis-service-name}")
	private String redisServiceName;

	@Bean
	RedisConnectionFactory connectionFactoryCloud() {
		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		RedisServiceInfo serviceInfo = (RedisServiceInfo) cloud.getServiceInfo(redisServiceName);
		String serviceID = serviceInfo.getId();
		return cloud.getServiceConnector(serviceID, RedisConnectionFactory.class, null);
	}


}
