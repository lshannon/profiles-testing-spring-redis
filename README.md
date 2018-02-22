# Introduction

This sample explores ways of working with a Redis dependancy when developing, testing and deploying to PCF. Within this samples some other key concepts of Core Spring are explored:

1. Creating a Component with Spring and customizing it's life cycle
2. Using Profiles to determine what components are wired together during tests and runtime
3. Add Swagger to Spring Boot
4. Working with MockMVC in test cases
5. Getting values from properties file
6. Working with Cloud Services in PCF
7. Working with Redis with Spring Data Repositories

A key aspect in this application is placing the Spring Data Redis Repository in a Service class. 

## Running with an Embedded Redis Process (Testing with Redis Dependancy)

When running the 'EmbeddedRedisControllerTest' test, a ConnectionFactory is created (based on setting the 'test'profile) that points to the Embedded Server. The EmbeddedRedis components wraps the redis.embedded.RedisServer, its injected into the ConnectionFactory bean and manages the Redis life cycle using @PostConstruct and @PreDestroy annotation.

Is an Embedded Redis for testing a good idea? Lets explore and learn some Spring at the same time.

### Pros

- Redis is included with the application, this means a CI/CD creating new environments with each build can run the tests
- Tests Redis object persistance and retrival (errors can occur here)
- Tests run against an actual Redis process

### Cons

- Requires extra dependancies and configuration
- Managed Redis does not always shut down cleanly - can have hanging processes

## Running with an Installed Redis (Testing with Redis Dependancy)

In many cases the Developer has Redis running on Docker or as a local processing (ie: brew). A developer may wish to run tests against this. The 'InstalledRedisControllerTest' demonstrates this. Its sets a 'dev' profile that creates a RedisConnectionFactory pointing to the installed Redis. The port for the installed Redis is configured in the application.properties file with the installed.redis.port key

### Pros

- Tests Redis object persistance and retrival (errors can occur here)

### Cons

- Requires an installed Redis in the environment

#### Note

Toos like Circle CI offer an options to do this as part of the build/test cycle

```shell

  - run:
      name: install redis
      command: |
        pushd /tmp
        wget http://download.redis.io/releases/redis-$REDIS_VERSION.tar.gz
        tar xzf redis-$REDIS_VERSION.tar.gz
        cd redis-$REDIS_VERSION && make
        src/redis-server --daemonize yes
        popd
      environment:
        REDIS_VERSION: 3.2.10

```

## Running On PCF (Integration Testing & Production)

Once we have tested its time to do integration testing and look at moving to production

For this sample PCF is the place where both of these environments exist. 

Thanks to the Spring Cloud Connectors project, a Redis service just needs to be bound to the application.

```shell

<!-- Starting PCF Connector Dependancies -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-spring-service-connector</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-cloudfoundry-connector</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-localconfig-connector</artifactId>
		</dependency>
		<!-- End PCF Connector Dependancies -->

```


The manifest.yml has a 'services' properties where the Redis service can be specified

```shell

---
applications:
- name: order-service
  memory: 1024M
  buildpack: java_buildpack
  random-route: true
  path: target/order-service.jar
  services:
  - orders

```

## Mocking The Redis (Testing with no Redis dependancy)

The MockServiceControllerTest Mocks the Order Service and Injects this controller into the Order Controller. From this point on the tests are completed using the Mock.

### Pros

- Service that previously required Redis is now Mocked allow this test to run in any environment
- Does not require extra config or dependancies around Redis

### Cons

- Does not tests Redis object persistance and retrival (errors can occur here)






