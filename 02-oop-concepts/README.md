# Object-Oriented Programming Concepts with Spring

This module demonstrates the four fundamental OOP concepts using Spring Framework integration. Each example shows how Spring enhances traditional OOP principles through dependency injection, component management, and configuration.

## 📁 Files Overview

### 1. EncapsulationExample.java
**Concept**: Data hiding and controlled access through getters/setters
- **Spring Features**: `@Component`, `@Value`, constructor injection
- **Key Demonstrations**:
  - Private fields with public accessor methods
  - Validation in setter methods
  - Spring-managed bean lifecycle
  - Property injection with default values

### 2. InheritanceExample.java
**Concept**: Code reuse through class hierarchies
- **Spring Features**: `@Component`, `@Service`, `@Autowired`
- **Key Demonstrations**:
  - Abstract base class (Vehicle) with concrete implementations
  - Method overriding with different behaviors
  - Spring managing inheritance hierarchies
  - Polymorphic collections in Spring services

### 3. PolymorphismExample.java
**Concept**: Runtime method resolution and interface-based programming
- **Spring Features**: `@Qualifier`, `@Primary`, multiple implementations
- **Key Demonstrations**:
  - Interface with multiple implementations
  - Runtime polymorphism through Spring DI
  - Qualifier-based bean selection
  - Primary bean designation

### 4. AbstractionExample.java
**Concept**: Hiding implementation details while exposing essential functionality
- **Spring Features**: `@Repository`, `@Service`, generic beans
- **Key Demonstrations**:
  - Abstract classes with template methods
  - Interface-based abstractions
  - Generic repository pattern
  - Spring's data access layer abstraction

## 🚀 Running the Examples

### Prerequisites
- Java 8 or higher
- Spring Framework dependencies
- Maven (if using the project structure)

### Compilation and Execution
```bash
# Navigate to the 02-oop-concepts directory
cd 02-oop-concepts

# Compile individual examples
javac -cp "path/to/spring-jars/*" EncapsulationExample.java
javac -cp "path/to/spring-jars/*" InheritanceExample.java
javac -cp "path/to/spring-jars/*" PolymorphismExample.java
javac -cp "path/to/spring-jars/*" AbstractionExample.java

# Run examples
java -cp ".:path/to/spring-jars/*" com.learning.oop.EncapsulationExample
java -cp ".:path/to/spring-jars/*" com.learning.oop.InheritanceExample
java -cp ".:path/to/spring-jars/*" com.learning.oop.PolymorphismExample
java -cp ".:path/to/spring-jars/*" com.learning.oop.AbstractionExample
```

## 📋 Example Outputs

### Encapsulation Example
```
=== Spring Encapsulation Example ===

1. Creating user with valid data:
User created: User{username='john_doe', email='john@example.com', age=25, role='USER'}
=== User Information ===
Display Name: john_doe (john@example.com)
Adult Status: Yes
Full Details: User{username='john_doe', email='john@example.com', age=25, role='USER'}

2. Updating user role:
User role updated: ADMIN

3. Attempting to create user with invalid data:
Validation Error: Username cannot be null or empty
```

### Inheritance Example
```
=== Spring Inheritance Example ===

Processing vehicle: Car
Car engine starting with key ignition...
Toyota Camry is starting...
=== Car Specifications ===
Vehicle: Toyota Camry (2023) - $25000.00
Doors: 4
Fuel Type: Gasoline
Toyota Camry is honking: Beep! Beep!
Toyota Camry has stopped.
```

### Polymorphism Example
```
=== Spring Polymorphism Example ===

==================================================
Processing: Transaction{amount=99.99, currency='USD', description='Premium Software License'}
Payment Method: Credit Card
==================================================
Transaction Fee: 2.90 USD
Total Amount: 102.89 USD
Refund Support: Yes

Processing Credit Card payment...
Charging 102.89 USD to credit card
✓ Credit Card payment successful!
Transaction completed successfully! ✓
```

### Abstraction Example
```
=== Spring Abstraction Example ===

=== Populating Sample Data ===
Entity saved: User
User created at: 2024-01-15T10:30:45.123
Entity saved: Product
Product inventory updated
Sample data populated successfully!

=== Working with User Repository ===
Saved: User{id=4, username='test_user', email='test@example.com'}
Total User count: 4
All Users: 4 found
```

## 🏗️ Spring Integration Highlights

### Dependency Injection Patterns
- **Constructor Injection**: Immutable dependencies
- **Qualifier-based Injection**: Multiple implementations
- **Primary Bean**: Default implementation selection

### Bean Management
- **Component Scanning**: Automatic bean discovery
- **Lifecycle Management**: Spring handles object creation/destruction
- **Scope Management**: Singleton, prototype scopes

### Configuration
- **Annotation-based**: `@Component`, `@Service`, `@Repository`
- **Java-based**: `@Configuration` classes
- **Property Injection**: `@Value` for externalized configuration

## 📚 Key Learning Points

### 1. Encapsulation + Spring
- Spring respects encapsulation principles
- Constructor injection promotes immutability
- Property validation through setters
- Spring manages bean state safely

### 2. Inheritance + Spring
- All levels of inheritance hierarchy can be Spring beans
- Method overriding works naturally with Spring
- Abstract classes can be partial Spring configurations
- Inheritance supports Spring's component model

### 3. Polymorphism + Spring
- Interface-based programming with Spring DI
- Multiple implementations managed by container
- Runtime implementation selection through qualifiers
- Loose coupling through abstraction

### 4. Abstraction + Spring
- Repository pattern with Spring annotations
- Template method pattern in abstract base classes
- Interface segregation with Spring beans
- Clean separation of concerns

## 🔧 Best Practices Demonstrated

1. **Interface Segregation**: Small, focused interfaces
2. **Dependency Inversion**: Depend on abstractions, not concretions
3. **Single Responsibility**: Each class has one clear purpose
4. **Open/Closed Principle**: Open for extension, closed for modification
5. **Spring Integration**: Leverage framework capabilities without compromising OOP

## 🧪 Testing the Examples

Each example includes validation scenarios and error handling to demonstrate:
- Input validation
- Exception handling
- Edge cases
- Spring container behavior

## 📖 Further Reading

- Spring Framework Documentation
- Effective Java by Joshua Bloch
- Design Patterns: Elements of Reusable Object-Oriented Software
- Spring in Action
- Clean Code by Robert C. Martin

---

*These examples demonstrate how Spring Framework enhances traditional OOP concepts while maintaining clean, maintainable code structure.* 