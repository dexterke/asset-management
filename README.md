# Asset Management API

Inventory Asset Management System

## Overview

This is a **test lab demo project** designed for educational and practice purposes, it serves as a playground for learning **Spring Boot, RESTful APIs, and related technologies**.

The **Asset Management API** is a RESTful web service that allows users to manage their assets. 

The API provides endpoints for **user authentication, user management, asset registration, and asset updates**.

The project follows the **MVC (Model-View-Controller) design pattern** and leverages **Spring Boot**, **Spring Data JPA**, and **SpringDoc OpenAPI** for documentation.

## Features

### ✅ **User Management**
- **Login** - Authenticate users using their email and password.
- **Register** - Create a new user account.
- **Get Users** - Retrieve user details.
- **Delete User** - Remove a user from the system.

### ✅ **Asset Management**
- **Get Assets** - List available assets.
- **Create Asset** - Add a new asset.
- **Update Asset** - Modify an existing asset’s details.
- **Delete Asset** - Remove an asset from the system.

### ✅ **Swagger API Documentation**
- **Swagger UI** available at: [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)
- **API Documentation**: [`http://localhost:8080/api-docs/`](http://localhost:8080/api-docs/)


## Tech Stack

- **Spring Boot 3.0.2** - Backend framework
- **Spring Data JPA** - ORM and database access
- **SpringDoc OpenAPI** - API documentation
- **Swagger UI** - Interactive API documentation
- **MySQL & H2 Database** - Database support
- **Lombok** - Code simplification

## Setup & Running the Application

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/your-repo/asset-management.git
   cd asset-management
   ```

2. **Configure Database (MySQL or H2)**
    - Set database details in `application.properties` or `application.yml`.

3. **Run the Application**
   ```sh
   ./gradlew bootRun
   ```

4. **Access API Documentation**
    - Swagger UI: [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)
    - OpenAPI Docs: [`http://localhost:8080/api-docs/`](http://localhost:8080/api-docs/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/)
* [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Java SpringBoot and JPA](https://www.oreilly.com/library/view/zero-to-hero/9781804616406)



