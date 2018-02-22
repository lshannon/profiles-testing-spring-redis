# Introduction



This sample explores ways of working with a Redis dependancy when developing, testing and deploying to PCF. Within this samples some other key concepts of Core Spring are explored:

1. Creating a Components with Spring and customizing it's life cycle
2. Using Profiles to determine what components are wired together when the application runs
3. Working with MockMVC in test cases
4. Getting values from properties file
5. Working with Cloud Services in PCF
6. Working with Redis with Spring Data Repositories

A key aspect in this application is placing the Spring Data Redis Repository in a Service class. 

## Running with an Embedded Redis Process

When running the 'EmbeddedRedisControllerTest' test, a ConnectionFactory is created (based on setting the 'test'profile) that points to the Embedded Server. The EmbeddedRedis components wraps the redis.embedded.RedisServer, its injected into the ConnectionFactory bean and manages the Redis life cycle using @PostConstruct and @PreDestroy annotation.

Is an Embedded Redis for testing a good idea?

### Pros

- Redis is included with the application, this means a CI/CD creating new environments with each build can run the tests
- Tests run against an actual Redis process

### Cons

- Requires extra dependancies and configuration
- Managed Redis does not always shut down cleanly - can have hanging processes

## Running with an Installed Redis

In many cases the Developer has Redis running on Docker or as a local processing (ie: brew). A developer may wish to run tests against this. The 'InstalledRedisControllerTest' demonstrates this. Its sets a 'dev' profile that creates a RedisConnectionFactory pointing to the installed Redis. The port for the installed Redis is configured in the application.properties file with the installed.redis.port key

## Running On PCF

Running on PCF is the easiest. Thanks to the Spring Cloud Connectors project, a Redis service just needs to be bound to the application. The manifest.yml has a services properties where the Redis service can be specified

## Mocking The Redis





