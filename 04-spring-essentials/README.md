# Spring Framework Essentials 🌱

## 📚 Overview
Spring Framework is a comprehensive programming and configuration model for modern Java-based enterprise applications. It provides infrastructure support for developing Java applications.

## 🎯 Core Concepts Covered

### 1. Inversion of Control (IoC) Container
- **BeanFactory**: Basic container interface
- **ApplicationContext**: Advanced container with additional features
- **Bean Definition**: Metadata for object creation
- **Bean Lifecycle**: Creation, initialization, destruction

### 2. Spring Beans
- **Bean Configuration**: XML, Java, and Annotation-based
- **Bean Scopes**: Singleton, Prototype, Request, Session
- **Bean Dependencies**: Constructor, Setter, Field injection
- **Lazy Initialization**: Load beans only when needed

### 3. Dependency Injection Types
- **Constructor Injection**: Dependencies via constructor parameters
- **Setter Injection**: Dependencies via setter methods
- **Field Injection**: Direct field injection using @Autowired

### 4. Core Annotations
- **@Component**: Generic stereotype annotation
- **@Service**: Service layer component
- **@Repository**: Data access layer component
- **@Controller**: Presentation layer component
- **@Configuration**: Java-based configuration
- **@Bean**: Bean definition in configuration class
- **@Autowired**: Automatic dependency injection
- **@Qualifier**: Specify which bean to inject
- **@Value**: Inject property values

### 5. Bean Lifecycle Annotations
- **@PostConstruct**: Called after dependency injection
- **@PreDestroy**: Called before bean destruction
- **@Scope**: Define bean scope
- **@Lazy**: Enable lazy initialization

## 📁 Examples Structure

```
04-spring-essentials/
├── basic-ioc/
│   ├── BasicIoCExample.java
│   ├── ApplicationContextExample.java
│   └── BeanLifecycleExample.java
├── bean-configuration/
│   ├── XMLConfigExample.java
│   ├── JavaConfigExample.java
│   └── AnnotationConfigExample.java
├── dependency-injection/
│   ├── ConstructorInjectionExample.java
│   ├── SetterInjectionExample.java
│   └── FieldInjectionExample.java
├── scopes/
│   ├── SingletonScopeExample.java
│   ├── PrototypeScopeExample.java
│   └── ScopeComparisonExample.java
├── annotations/
│   ├── ComponentScanExample.java
│   ├── QualifierExample.java
│   └── ValueInjectionExample.java
├── lifecycle/
│   ├── InitDestroyExample.java
│   ├── PostConstructPreDestroyExample.java
│   └── BeanLifecycleCallbacksExample.java
├── resources/
│   ├── application-context.xml
│   ├── beans.xml
│   └── application.properties
└── tests/
    ├── SpringContextTest.java
    ├── DependencyInjectionTest.java
    └── BeanScopeTest.java
```

## 🎓 Learning Objectives

By completing this section, you will understand:
- How Spring IoC container manages object creation and dependencies
- Different ways to configure Spring beans
- How dependency injection works in Spring
- Bean scopes and their use cases
- Spring annotations and their purposes
- Bean lifecycle and callback methods

## 🔍 Key Benefits of Spring Framework

### 1. Inversion of Control
- Objects don't create their dependencies
- Container manages object lifecycle
- Promotes loose coupling

### 2. Dependency Injection
- Automatic dependency resolution
- Multiple injection types supported
- Easy testing with mock objects

### 3. Configuration Flexibility
- XML, Java, and Annotation-based configuration
- Externalized configuration support
- Profile-based configuration

### 4. Enterprise Features
- Transaction management
- Security integration
- Data access abstraction
- Web MVC framework

## 🚀 Spring vs Traditional Java

### Traditional Java Problems
```java
// Tight coupling
public class OrderService {
    private EmailService emailService = new EmailService(); // Hard-coded dependency
    private PaymentService paymentService = new PaymentService();
}
```

### Spring Solution
```java
// Loose coupling with DI
@Service
public class OrderService {
    private final EmailService emailService;
    private final PaymentService paymentService;
    
    @Autowired
    public OrderService(EmailService emailService, PaymentService paymentService) {
        this.emailService = emailService;
        this.paymentService = paymentService;
    }
}
```

## 🔧 Configuration Comparison

### XML Configuration
- Explicit and verbose
- Centralized configuration
- Good for complex scenarios

### Java Configuration
- Type-safe configuration
- IDE support with auto-completion
- Better refactoring support

### Annotation Configuration
- Minimal configuration
- Convention over configuration
- Faster development

---

**Previous**: [Design Patterns](../03-design-patterns/README.md) | **Next**: [Spring Annotations](../05-spring-annotations/README.md) 