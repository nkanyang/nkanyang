
## Content
- [Problem Description](#Problem-Description)
- [Tech Stack](#Tech-Stack)
- [Project Build, Test, Package and Run](#Project-Build-Test-Package-and-Run)
- [API Documentation](#API-Documentation)
- [Improvements in Refactoring](#Improvements-in-Refactoring)


## Problem Description

There should be these endpoints:

1. `GET /products` - gets all products.
2. `GET /products?name={name}` - finds all products matching the specified name.
3. `GET /products/{id}` - gets the project that matches the specified ID - ID is a GUID.
4. `POST /products` - creates a new product.
5. `PUT /products/{id}` - updates a product.
6. `DELETE /products/{id}` - deletes a product and its options.
7. `GET /products/{id}/options` - finds all options for a specified product.
8. `GET /products/{id}/options/{optionId}` - finds the specified product option for the specified product.
9. `POST /products/{id}/options` - adds a new product option to the specified product.
10. `PUT /products/{id}/options/{optionId}` - updates the specified product option.
11. `DELETE /products/{id}/options/{optionId}` - deletes the specified product option.

All models are specified in the `/Models` folder, but should conform to:

**Product:**
```
{
  "Id": "01234567-89ab-cdef-0123-456789abcdef",
  "Name": "Product name",
  "Description": "Product description",
  "Price": 123.45,
  "DeliveryPrice": 12.34
}
```

**Products:**
```
{
  "Items": [
    {
      // product
    },
    {
      // product
    }
  ]
}
```

**Product Option:**
```
{
  "Id": "01234567-89ab-cdef-0123-456789abcdef",
  "Name": "Product name",
  "Description": "Product description"
}
```

**Product Options:**
```
{
  "Items": [
    {
      // product option
    },
    {
      // product option
    }
  ]
}
```


## Tech Stack

- Java Spring Boot Framework
- JPA/Hibernate + SQLite
- Lombok + MapStruct
- Swagger UI
- JUnit + Mockito
- Docker

## Project Build, Test, Package and Run

### Dependencies

- Project management tool: Maven 3.6.3
- JDK Version: Java 14

### Build
compile the project
```
mvn compile
```

### Test
run all the tests (unit tests and integration tests)
```
mvn test
```

### Package
compile, run tests and build the artifact in ```./target/ ```
```
mvn package
```
### Run
Start the app for API call
```
java -jar ./target/refactor-this-0.0.1-SNAPSHOT.jar
```

## API Documentation

 **Swagger UI** is introduced to help users to have a better understanding of what the APIs can do. It can also used as a tool to test the APIs.

The endpoint for Swagger is ```http://hostname:port/swagger-ui.html```

## Improvements in Refactoring

This is a Spring Boot project, refactored from the given Web API project which built in .NET,  and it improves as following:

- Use JPA + Hibernate, which provides a database independent abstraction on top of SQL, to decouple the service and database implementation.

- Introduce data transfer object (DTO) to hide unnecessary/sensitive data of entity/model from client, resulting in both loose coupling and optimized data transfer.

- Add validation when receiving data from the client and specify the HTTP status code for each API response.

- Use fine-grained, configurable logging framework.

- Use configuration file instead of hard coding parameters(eg: database connect string).

- Add customized exceptions to handle service errors

- Add unit test with the coverage rate up to 100% (lines and methods) for main service class

- Use Swagger annotations to add documentation of APIs in code and demonstrate via Swagger UI

- Prepare the docker file for the service to run in docker

