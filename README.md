# jetty-openapi-swagger-helloworld

## Tech Stack
- Java 17
- Jetty 12
- Jersey 3.1
- Swagger 2.2
- openapi v3

## overview

Simple skeleton 'Hello World' REST API service in Java 17 with latest Jetty, jersey, swagger and openapi dependencies.
Contains static swagger-ui application.
Focus is on hosting REST API and Swagger-UI explicitly, without Spring Boot.

Contains just 3 classes and the static swagger-ui application in the resource folder.

- HelloWorldEndpoint.java : hello world REST API handler annotated with REST API metadata
- SwaggerConfig.java : jersey config with swagger openapi
- JettyServer.java : Jetty Http Server serving 3 servlets:
    - / : the actual Hello World REST API service : http://localhost:8080/hello
    - /api/ : the V3 openapi specification of the Hello World REST API : http://localhost:8080/api/
    - /swagger-ui/ : the swagger-ui application pointing to the Hello World REST API : http://localhost:8080/swagger-ui/index.html
    
## references
https://github.com/swagger-api/swagger-ui
https://github.com/jetty/jetty.project/
https://github.com/eclipse-ee4j/jersey