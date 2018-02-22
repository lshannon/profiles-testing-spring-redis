# Sample Description

This sample explores ways of working with a Redis dependancy when developing, testing and deploying to PCF.

A few key concepts explored here from a Spring points of view.

1. Creating a Components with Spring
2. Using Profiles to determine what components wired together when the application runs
3. Working with MockMVC in a test case
4. Getting values from properties file

## Testing Profile

When running tests, the 'testing' profile is set. This profile creates a ConnectionFactory pointed at the Embedded Server. The EmbeddedRedis components wraps the redis.embedded.RedisServer and manages its life cycle using @PostConstruct and @PreDestroy. It gets the properties from the application.properties file using @Value

### Pros

- Redis is included with the application
- Tests run against Redis in any environment

### Cons

- Extra dependancies and configuration
- Managed Redis does not always shut down cleanly

## Dev Profile

This is when running the application locally (manual testing perhaps) and you wish to use an Redis you have installed and configured outside of the application. This creates a RedisConnectionFactory that points to the installed Redis. The port for the installed Redis is configured in the application.properties file

## Cloud Profile

This profile is set when the application runs in PCF. It looks for a Redis service defined in the space the application is running

## Mocking



