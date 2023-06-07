# Spring Boot Tests

Spring Boot tests and examples.

## Integration Tests

### Whole Application Context

The `ApplicationIntegrationTest` class is an example of how to write integration tests for a Spring Boot application.
It uses;

* the `@SpringBootTest` annotation to load the whole application context with `WebEnvironment.MOCK` mock servlet
  environment.
* `AutoConfigureMockMvc` to inject a `MockMvc` instance to perform HTTP requests against the application.
* `TestPropertySource` to override the application properties.

The `application-integration-test.properties` file is used to override the application properties for the integration
tests.
We use an in-memory H2 database for the integration tests.

### Partial Test Context

The `EmployeeIntegrationTest` class is an example of how to write integration tests for a Spring Boot application using
a test context.

It imports `EmployeeTestContextConfiguration` class which creates a test context with the `EmployeeNoOpService`
bean as the implementation of the `EmployeeService` interface.
