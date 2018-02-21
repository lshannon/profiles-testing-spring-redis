# Sample Description

This sample explores ways of working with a Redis dependancy when developing, testing and deploying to PCF.

A few key concepts explored here from a Spring points of view.

1. Creating a Components with Spring
2. Using Profiles to determine what components wired together when the application runs
3. Working with MockMVC in a test case

## Testing Profile

When running tests, the 'testing' profile is set. This profile starts up a Embedded Redis Server and creates a RedisConnectionFactory that points to that service

## Dev Profile

This is when running the application locally to manual testing and demonstration. This assumes there is an installed Redis service running and creates a RedisConnectionFactory that points to that.

## Cloud Profile

This profile is set when the application runs in PCF. It looks for a Redis service defined in the space the application is running.

