# Sample Description

This sample is a Redis backed basic Spring Boot application that uses Spring Data Redis (Repositories) to perform CRUD operations and Spring MVC to exposes the data via REST endpoints.

For Redis this sample uses an Embedded Redis as the local/dev store and a Service broker provisioned Redis on Pivotal Web Services (PWS).

## Testing Profile

When running tests, the 'testing' profile is set. This profile starts up a Embedded Redis Server and creates a RedisConnectionFactory that points to that service

## Dev Profile

This is when running the application locally to manual testing and demonstration. This assumes there is an installed Redis service running and creates a RedisConnectionFactory that points to that.

## Cloud Profile

This profile is set when the application runs in PCF. It looks for a Redis service defined in the space the application is running.

