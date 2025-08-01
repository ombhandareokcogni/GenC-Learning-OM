# Integration Examples 🔗

## 📚 Overview
This section contains complete, working Spring Boot applications that integrate all the concepts learned in previous sections. These examples demonstrate real-world application patterns and best practices.

## 🎯 Integration Projects

### 1. Task Management System
- **Technology Stack**: Spring Boot, Spring Data JPA, H2 Database, Thymeleaf
- **Patterns Used**: Repository, Service Layer, MVC, Dependency Injection
- **Features**: CRUD operations, validation, exception handling, testing

### 2. E-Commerce Product Catalog
- **Technology Stack**: Spring Boot, REST APIs, JSON, Lombok, Mockito
- **Patterns Used**: Factory, Builder, Strategy, DAO
- **Features**: RESTful services, JSON serialization, comprehensive testing

### 3. User Authentication System
- **Technology Stack**: Spring Security, JWT, BCrypt, MySQL
- **Patterns Used**: Proxy, Decorator, Command
- **Features**: Authentication, authorization, password encryption, role-based access

### 4. Microservices Communication
- **Technology Stack**: Spring Cloud, REST Template, Circuit Breaker
- **Patterns Used**: Gateway, Service Discovery, Load Balancing
- **Features**: Inter-service communication, fault tolerance, monitoring

## 📁 Project Structure

```
09-integration-examples/
├── task-management-system/
│   ├── src/main/java/
│   │   ├── TaskManagementApplication.java
│   │   ├── controller/
│   │   │   ├── TaskController.java
│   │   │   └── UserController.java
│   │   ├── service/
│   │   │   ├── TaskService.java
│   │   │   └── UserService.java
│   │   ├── repository/
│   │   │   ├── TaskRepository.java
│   │   │   └── UserRepository.java
│   │   ├── model/
│   │   │   ├── Task.java
│   │   │   ├── User.java
│   │   │   └── TaskStatus.java
│   │   ├── dto/
│   │   │   ├── TaskDto.java
│   │   │   └── UserDto.java
│   │   ├── config/
│   │   │   ├── DatabaseConfig.java
│   │   │   └── WebConfig.java
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java
│   │       └── CustomExceptions.java
│   ├── src/test/java/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   └── integration/
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   ├── data.sql
│   │   └── templates/
│   └── pom.xml
├── ecommerce-catalog/
│   ├── src/main/java/
│   │   ├── CatalogApplication.java
│   │   ├── api/
│   │   │   └── ProductController.java
│   │   ├── service/
│   │   │   ├── ProductService.java
│   │   │   ├── CategoryService.java
│   │   │   └── PricingService.java
│   │   ├── repository/
│   │   │   ├── ProductRepository.java
│   │   │   └── CategoryRepository.java
│   │   ├── model/
│   │   │   ├── Product.java
│   │   │   ├── Category.java
│   │   │   └── Price.java
│   │   ├── dto/
│   │   │   ├── ProductDto.java
│   │   │   └── ApiResponse.java
│   │   └── config/
│   │       ├── JacksonConfig.java
│   │       └── SwaggerConfig.java
│   ├── src/test/java/
│   └── pom.xml
├── user-authentication/
│   ├── src/main/java/
│   │   ├── AuthApplication.java
│   │   ├── security/
│   │   │   ├── SecurityConfig.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   └── JwtTokenProvider.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   └── UserController.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   └── UserDetailsServiceImpl.java
│   │   ├── model/
│   │   │   ├── User.java
│   │   │   ├── Role.java
│   │   │   └── Permission.java
│   │   └── dto/
│   │       ├── LoginRequest.java
│   │       ├── SignupRequest.java
│   │       └── JwtResponse.java
│   ├── src/test/java/
│   └── pom.xml
└── microservices-demo/
    ├── eureka-server/
    ├── api-gateway/
    ├── user-service/
    ├── product-service/
    └── docker-compose.yml
```

## 🎓 Learning Objectives

By studying these integration examples, you will understand:
- How to structure real-world Spring Boot applications
- How to integrate multiple Spring modules effectively
- How to implement proper layered architecture
- How to write comprehensive integration tests
- How to handle cross-cutting concerns (security, logging, validation)
- How to create RESTful APIs with proper error handling
- How to work with databases using Spring Data JPA
- How to implement authentication and authorization
- How to create microservices architecture

## 🏗️ Architecture Patterns Demonstrated

### Layered Architecture
```
┌─────────────────┐
│   Presentation  │ ← Controllers, REST APIs
├─────────────────┤
│    Business     │ ← Services, Domain Logic
├─────────────────┤
│   Persistence   │ ← Repositories, DAOs
├─────────────────┤
│    Database     │ ← H2, MySQL, PostgreSQL
└─────────────────┘
```

### Dependency Flow
```
Controller → Service → Repository → Database
    ↓         ↓          ↓
   DTOs   Domain      Entities
          Objects
```

## 🧪 Testing Strategy

### Test Types per Layer
- **Controller Tests**: `@WebMvcTest`, MockMvc
- **Service Tests**: Unit tests with `@Mock`
- **Repository Tests**: `@DataJpaTest`
- **Integration Tests**: `@SpringBootTest`
- **End-to-End Tests**: TestContainers, REST Assured

### Test Coverage Goals
- **Unit Tests**: 80%+ coverage
- **Integration Tests**: Critical user journeys
- **Performance Tests**: Load testing for APIs
- **Security Tests**: Authentication/authorization scenarios

## 🚀 Key Features Demonstrated

### 1. Configuration Management
- **Profiles**: dev, test, prod environments
- **External Configuration**: application.yml, environment variables
- **Type-safe Properties**: `@ConfigurationProperties`

### 2. Data Management
- **JPA Entities**: Proper mapping annotations
- **Repository Patterns**: Custom queries, specifications
- **Transaction Management**: `@Transactional` usage
- **Database Migration**: Flyway/Liquibase integration

### 3. API Design
- **RESTful Principles**: Proper HTTP methods and status codes
- **API Documentation**: Swagger/OpenAPI integration
- **Error Handling**: Global exception handlers
- **Validation**: Bean Validation (JSR-303)

### 4. Security Implementation
- **Authentication**: JWT tokens, session management
- **Authorization**: Role-based access control
- **Password Security**: BCrypt hashing
- **CORS Configuration**: Cross-origin resource sharing

### 5. Monitoring and Observability
- **Actuator Endpoints**: Health checks, metrics
- **Logging**: Structured logging with Logback
- **Monitoring**: Micrometer metrics
- **Tracing**: Distributed tracing setup

## 📋 Prerequisites

Before exploring these examples, ensure you understand:
- Java Core concepts (from section 01)
- OOP principles (from section 02)
- Design patterns (from section 03)
- Spring Framework basics (from section 04)
- Testing fundamentals (from section 07)
- Maven build tool (from section 08)

## 🎯 How to Use These Examples

### 1. Study the Structure
- Examine the project organization
- Understand the layer separation
- Review the dependency relationships

### 2. Run the Applications
- Follow the README in each project
- Start with the simplest example
- Experiment with the endpoints

### 3. Examine the Tests
- Study different testing approaches
- Run the test suites
- Write additional tests

### 4. Modify and Extend
- Add new features
- Implement additional endpoints
- Practice refactoring

### 5. Deploy and Monitor
- Package the applications
- Deploy to different environments
- Monitor performance and logs

## 🌟 Best Practices Highlighted

### Code Organization
- **Package by Feature**: Not by layer type
- **Single Responsibility**: Each class has one reason to change
- **Dependency Injection**: Constructor injection preferred
- **Immutable Objects**: Use `final` fields where possible

### Error Handling
- **Global Exception Handling**: Centralized error processing
- **Meaningful Error Messages**: User-friendly responses
- **Proper HTTP Status Codes**: RESTful error responses
- **Logging**: Appropriate log levels and structured logging

### Performance Considerations
- **Database Queries**: Avoid N+1 problems
- **Caching**: Strategic use of Spring Cache
- **Connection Pooling**: Proper database connection management
- **Lazy Loading**: JPA lazy loading strategies

---

**Previous**: [Maven Projects](../08-maven-projects/README.md) | **Next**: [Additional Tools](../10-additional-tools/README.md) 