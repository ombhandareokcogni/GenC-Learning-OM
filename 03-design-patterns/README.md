# Design Patterns Examples

This module demonstrates fundamental design patterns with clear, commented examples that show practical usage and benefits.

## 📁 New Pattern Examples

### 🗄️ DAO Pattern (`dao/DAOPatternExample.java`)
**Data Access Object Pattern**

**Purpose**: Separates business logic from data access logic

**Key Components**:
- **Entity**: `Student` class representing data structure
- **DAO Interface**: `StudentDAO` defining data operations contract
- **DAO Implementation**: `StudentDAOImpl` with in-memory storage
- **Alternative Implementation**: `DatabaseStudentDAO` showing flexibility
- **Service Layer**: `StudentService` using DAO for business operations

**Benefits Demonstrated**:
- ✅ Separation of concerns
- ✅ Easy to switch data sources (memory, database, web service)
- ✅ Testability through interface abstraction
- ✅ Single responsibility for data operations
- ✅ CRUD operations standardization

**Usage Example**:
```java
// Create DAO and inject into service
StudentDAO dao = new StudentDAOImpl();
StudentService service = new StudentService(dao);

// Use business operations
service.enrollStudent("John Doe", "john@example.com", "Computer Science");
service.generateCourseReport("Computer Science");
```

### 🔄 IoC Pattern (`ioc/IoCPatternExample.java`)
**Inversion of Control / Dependency Injection Pattern**

**Purpose**: Inverts control of object creation and dependency management

**Key Components**:
- **Service Interfaces**: `Logger`, `EmailService`, `DatabaseService`
- **Multiple Implementations**: Console/File loggers, SMTP/Mock email services
- **Bad Example**: `BadUserService` with tight coupling
- **Good Example**: `UserService` with dependency injection
- **IoC Container**: `SimpleIoCContainer` for dependency management
- **Configuration**: `ApplicationConfig` for different environments

**Benefits Demonstrated**:
- ✅ Loose coupling between components
- ✅ Better testability through mock injection
- ✅ Configuration flexibility (dev/test/prod environments)
- ✅ Single Responsibility Principle adherence
- ✅ Runtime dependency switching

**Usage Example**:
```java
// Setup IoC container
SimpleIoCContainer container = ApplicationConfig.setupContainer();

// Create service with injected dependencies
UserService userService = container.createUserService();

// Use business operations
userService.registerUser("alice", "alice@example.com");
```

### 🎭 Proxy Pattern (`proxy/ProxyPatternExample.java`)
**Proxy Pattern with Multiple Types**

**Purpose**: Provides placeholder/surrogate to control access to objects

**Key Components**:
- **Subject Interface**: `DocumentService` defining operations
- **Real Subject**: `RealDocumentService` (expensive object)
- **Virtual Proxy**: `VirtualDocumentProxy` for lazy loading
- **Protection Proxy**: `ProtectionDocumentProxy` for access control
- **Caching Proxy**: `CachingDocumentProxy` for performance
- **Remote Proxy**: `RemoteDocumentProxy` for distributed systems

**Types Demonstrated**:

1. **🎭 Virtual Proxy**: Lazy initialization
   - Defers expensive object creation until needed
   - Improves application startup time
   - Memory optimization

2. **🛡️ Protection Proxy**: Access control
   - Role-based permissions (Admin, Editor, Viewer, Guest)
   - Security enforcement
   - Audit logging capabilities

3. **💾 Caching Proxy**: Performance optimization
   - Results caching with expiration
   - Cache hit/miss tracking
   - Automatic cache invalidation

4. **🌐 Remote Proxy**: Distributed systems
   - Location transparency
   - Network call simulation
   - Connection management

**Usage Example**:
```java
// Virtual Proxy - lazy loading
DocumentService proxy = new VirtualDocumentProxy();
String content = proxy.loadDocument("DOC001"); // Real service created here

// Protection Proxy - access control
DocumentService secureProxy = new ProtectionDocumentProxy("admin");
secureProxy.deleteDocument("DOC001"); // Allowed for admin

// Caching Proxy - performance
DocumentService cachedProxy = new CachingDocumentProxy();
cachedProxy.loadDocument("DOC001"); // Cache miss - slow
cachedProxy.loadDocument("DOC001"); // Cache hit - fast
```

## 🚀 Running the Examples

### Prerequisites
- Java 8 or higher
- Basic understanding of OOP concepts

### Compilation and Execution

#### DAO Pattern
```bash
cd dao/
javac DAOPatternExample.java
java com.learning.patterns.dao.DAOPatternExample
```

#### IoC Pattern
```bash
cd ioc/
javac IoCPatternExample.java
java com.learning.patterns.ioc.IoCPatternExample
```

#### Proxy Pattern
```bash
cd proxy/
javac ProxyPatternExample.java
java com.learning.patterns.proxy.ProxyPatternExample
```

## 📋 Example Outputs

### DAO Pattern Output
```
============================================================
             DAO PATTERN DEMONSTRATION
============================================================

📝 1. ENROLLING STUDENTS
------------------------------
✓ Student saved: Student{id=1, name='John Doe', email='john@example.com', course='Computer Science'}
✓ Student saved: Student{id=2, name='Jane Smith', email='jane@example.com', course='Mathematics'}

📊 3. GENERATING REPORTS
------------------------------
==================================================
COURSE REPORT: COMPUTER SCIENCE
==================================================
1. Student{id=1, name='John Doe', email='john@example.com', course='Computer Science'}
Total Students: 1
```

### IoC Pattern Output
```
======================================================================
              IoC (INVERSION OF CONTROL) DEMONSTRATION
======================================================================

❌ 1. TIGHT COUPLING EXAMPLE (WITHOUT IoC)
--------------------------------------------------
📝 [LOG] Registering user: john_doe
💾 [MEMORY DB] Saved to users: User{username='john_doe', email='john@example.com'} (ID: ID_1736331234567)

✅ 2. IoC SOLUTION - DEPENDENCY INJECTION
--------------------------------------------------
🔧 [IoC CONTAINER] Registered: Logger -> ConsoleLogger
🔧 [IoC CONTAINER] Registered: EmailService -> SMTPEmailService
🔧 [IoC CONTAINER] Registered: DatabaseService -> InMemoryDatabaseService
```

### Proxy Pattern Output
```
======================================================================
                    PROXY PATTERN DEMONSTRATION
======================================================================

🎭 1. VIRTUAL PROXY DEMONSTRATION
--------------------------------------------------
🎭 [VIRTUAL PROXY] Created (real service not yet initialized)

⏱️ Proxy created instantly! Real service not yet initialized.
Now making first request (this will trigger real service creation):
🎭 [VIRTUAL PROXY] First access - initializing real service...
🏗️ [REAL SERVICE] Initializing expensive DocumentService...
✅ [REAL SERVICE] DocumentService initialized successfully!
```

## 🎯 Key Learning Points

### Design Pattern Benefits
- **Code Reusability**: Patterns provide proven solutions
- **Maintainability**: Well-structured, easy to modify code
- **Communication**: Common vocabulary for developers
- **Best Practices**: Industry-standard approaches

### When to Use Each Pattern

**DAO Pattern**:
- ✅ When you need database operations
- ✅ Multiple data sources (DB, file, web service)
- ✅ Testable data access layer
- ✅ Business logic separation

**IoC Pattern**:
- ✅ When you have dependencies between classes
- ✅ Need different configurations (dev/test/prod)
- ✅ Want to improve testability
- ✅ Loose coupling requirements

**Proxy Pattern**:
- ✅ Expensive object creation (Virtual)
- ✅ Access control needed (Protection)
- ✅ Performance optimization (Caching)
- ✅ Remote system access (Remote)

## 📚 Additional Patterns in This Module

### Existing Examples
- **Factory Pattern** (`factory/`) - Object creation abstraction
- **Dependency Injection** (`dependency-injection/`) - Constructor injection examples

## 🔧 Best Practices Demonstrated

1. **Interface Segregation**: Small, focused interfaces
2. **Dependency Inversion**: Depend on abstractions, not concretions
3. **Single Responsibility**: Each class has one clear purpose
4. **Open/Closed Principle**: Open for extension, closed for modification
5. **Clear Documentation**: Comprehensive comments and examples

## 📖 Further Reading

- "Design Patterns: Elements of Reusable Object-Oriented Software" (Gang of Four)
- "Head First Design Patterns" by Freeman & Robson
- "Effective Java" by Joshua Bloch
- "Clean Code" by Robert C. Martin

---

*These examples demonstrate practical applications of design patterns with clear benefits and real-world scenarios.* 