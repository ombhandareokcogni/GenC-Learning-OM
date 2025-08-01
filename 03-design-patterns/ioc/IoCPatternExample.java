package com.learning.patterns.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IoC (Inversion of Control) Pattern Example
 * 
 * Purpose: Inverts the control of object creation and dependency management
 * Benefits:
 * - Loose coupling between components
 * - Better testability through dependency injection
 * - Easier configuration and management
 * - Single Responsibility Principle adherence
 */

// Step 1: Define service interfaces (abstractions)

// Logging service interface
interface Logger {
    void log(String message);
    void error(String message);
}

// Email service interface
interface EmailService {
    void sendEmail(String to, String subject, String body);
}

// Database service interface
interface DatabaseService {
    void save(String table, String data);
    String fetch(String table, String id);
}

// Step 2: Concrete implementations of services

// Console logger implementation
class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("📝 [LOG] " + message);
    }
    
    @Override
    public void error(String message) {
        System.err.println("❌ [ERROR] " + message);
    }
}

// File logger implementation (alternative)
class FileLogger implements Logger {
    private String filename;
    
    public FileLogger(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void log(String message) {
        System.out.println("📄 [FILE LOG -> " + filename + "] " + message);
    }
    
    @Override
    public void error(String message) {
        System.out.println("📄 [FILE ERROR -> " + filename + "] " + message);
    }
}

// SMTP email service implementation
class SMTPEmailService implements EmailService {
    private String smtpServer;
    
    public SMTPEmailService(String smtpServer) {
        this.smtpServer = smtpServer;
    }
    
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("📧 [SMTP: " + smtpServer + "] Sending email to: " + to);
        System.out.println("   Subject: " + subject);
        System.out.println("   Body: " + body);
    }
}

// Mock email service (for testing)
class MockEmailService implements EmailService {
    private List<String> sentEmails = new ArrayList<>();
    
    @Override
    public void sendEmail(String to, String subject, String body) {
        String email = "To: " + to + ", Subject: " + subject;
        sentEmails.add(email);
        System.out.println("🧪 [MOCK EMAIL] Simulated sending: " + email);
    }
    
    public List<String> getSentEmails() {
        return sentEmails;
    }
}

// In-memory database service
class InMemoryDatabaseService implements DatabaseService {
    private Map<String, Map<String, String>> tables = new HashMap<>();
    
    @Override
    public void save(String table, String data) {
        tables.computeIfAbsent(table, k -> new HashMap<>());
        String id = "ID_" + System.currentTimeMillis();
        tables.get(table).put(id, data);
        System.out.println("💾 [MEMORY DB] Saved to " + table + ": " + data + " (ID: " + id + ")");
    }
    
    @Override
    public String fetch(String table, String id) {
        String data = tables.getOrDefault(table, new HashMap<>()).get(id);
        System.out.println("💾 [MEMORY DB] Fetched from " + table + " (ID: " + id + "): " + data);
        return data;
    }
}

// Step 3: Business service that depends on other services
// WITHOUT IoC - Tight coupling (BAD example)
class BadUserService {
    
    // Hard-coded dependencies - tight coupling
    private Logger logger = new ConsoleLogger();
    private EmailService emailService = new SMTPEmailService("smtp.company.com");
    private DatabaseService databaseService = new InMemoryDatabaseService();
    
    public void registerUser(String username, String email) {
        logger.log("Registering user: " + username);
        
        // Business logic
        String userData = "User{username='" + username + "', email='" + email + "'}";
        databaseService.save("users", userData);
        
        // Send welcome email
        emailService.sendEmail(email, "Welcome!", "Welcome to our platform, " + username + "!");
        
        logger.log("User registered successfully: " + username);
    }
}

// Step 4: WITH IoC - Loose coupling (GOOD example)
class UserService {
    
    // Dependencies injected from outside (Dependency Injection)
    private final Logger logger;
    private final EmailService emailService;
    private final DatabaseService databaseService;
    
    // Constructor Injection - IoC container will provide dependencies
    public UserService(Logger logger, EmailService emailService, DatabaseService databaseService) {
        this.logger = logger;
        this.emailService = emailService;
        this.databaseService = databaseService;
        
        logger.log("UserService initialized with injected dependencies");
    }
    
    public void registerUser(String username, String email) {
        logger.log("Starting user registration for: " + username);
        
        try {
            // Validate input
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            
            // Business logic
            String userData = "User{username='" + username + "', email='" + email + "'}";
            databaseService.save("users", userData);
            
            // Send welcome email
            emailService.sendEmail(email, "Welcome to Our Platform!", 
                "Hello " + username + ",\n\nWelcome to our amazing platform!\n\nBest regards,\nThe Team");
            
            logger.log("User registered successfully: " + username);
            
        } catch (Exception e) {
            logger.error("Failed to register user " + username + ": " + e.getMessage());
            throw e;
        }
    }
    
    public void resetPassword(String username, String email) {
        logger.log("Password reset requested for: " + username);
        
        // Generate reset token (simplified)
        String resetToken = "TOKEN_" + System.currentTimeMillis();
        databaseService.save("reset_tokens", resetToken);
        
        // Send reset email
        emailService.sendEmail(email, "Password Reset", 
            "Hi " + username + ",\n\nYour password reset token is: " + resetToken + 
            "\n\nThis token will expire in 24 hours.\n\nBest regards,\nThe Team");
        
        logger.log("Password reset email sent to: " + username);
    }
}

// Step 5: Simple IoC Container (Dependency Injection Container)
class SimpleIoCContainer {
    
    private Map<Class<?>, Object> services = new HashMap<>();
    
    // Register service instance
    public <T> void register(Class<T> serviceClass, T implementation) {
        services.put(serviceClass, implementation);
        System.out.println("🔧 [IoC CONTAINER] Registered: " + serviceClass.getSimpleName() + 
                          " -> " + implementation.getClass().getSimpleName());
    }
    
    // Get service instance
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> serviceClass) {
        T service = (T) services.get(serviceClass);
        if (service == null) {
            throw new IllegalArgumentException("Service not registered: " + serviceClass.getSimpleName());
        }
        return service;
    }
    
    // Factory method to create UserService with all dependencies
    public UserService createUserService() {
        Logger logger = get(Logger.class);
        EmailService emailService = get(EmailService.class);
        DatabaseService databaseService = get(DatabaseService.class);
        
        return new UserService(logger, emailService, databaseService);
    }
}

// Step 6: Configuration class - configures dependencies
class ApplicationConfig {
    
    public static SimpleIoCContainer setupContainer() {
        SimpleIoCContainer container = new SimpleIoCContainer();
        
        // Register dependencies
        container.register(Logger.class, new ConsoleLogger());
        container.register(EmailService.class, new SMTPEmailService("smtp.production.com"));
        container.register(DatabaseService.class, new InMemoryDatabaseService());
        
        return container;
    }
    
    public static SimpleIoCContainer setupTestContainer() {
        SimpleIoCContainer container = new SimpleIoCContainer();
        
        // Register test dependencies
        container.register(Logger.class, new FileLogger("test.log"));
        container.register(EmailService.class, new MockEmailService());
        container.register(DatabaseService.class, new InMemoryDatabaseService());
        
        return container;
    }
}

// Main class - demonstrates IoC pattern
public class IoCPatternExample {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("              IoC (INVERSION OF CONTROL) DEMONSTRATION");
        System.out.println("=".repeat(70));
        
        // Demonstrate the problem with tight coupling
        demonstrateTightCoupling();
        
        // Demonstrate IoC solution
        demonstrateIoC();
        
        // Demonstrate flexibility of IoC
        demonstrateFlexibility();
        
        showIoCBenefits();
    }
    
    private static void demonstrateTightCoupling() {
        System.out.println("\n❌ 1. TIGHT COUPLING EXAMPLE (WITHOUT IoC)");
        System.out.println("-".repeat(50));
        
        System.out.println("Problem: Dependencies are hard-coded inside the class");
        
        BadUserService badService = new BadUserService();
        badService.registerUser("john_doe", "john@example.com");
        
        System.out.println("\nIssues with tight coupling:");
        System.out.println("• Hard to test (can't mock dependencies)");
        System.out.println("• Hard to change implementations");
        System.out.println("• Violates Single Responsibility Principle");
        System.out.println("• Creates unnecessary object creation overhead");
    }
    
    private static void demonstrateIoC() {
        System.out.println("\n✅ 2. IoC SOLUTION - DEPENDENCY INJECTION");
        System.out.println("-".repeat(50));
        
        // Setup IoC container with production dependencies
        SimpleIoCContainer container = ApplicationConfig.setupContainer();
        
        // Create service with injected dependencies
        UserService userService = container.createUserService();
        
        System.out.println("\n🚀 Running with production configuration:");
        userService.registerUser("alice_smith", "alice@example.com");
        userService.resetPassword("alice_smith", "alice@example.com");
    }
    
    private static void demonstrateFlexibility() {
        System.out.println("\n🔄 3. DEMONSTRATING IoC FLEXIBILITY");
        System.out.println("-".repeat(50));
        
        // Setup different configuration for testing
        SimpleIoCContainer testContainer = ApplicationConfig.setupTestContainer();
        UserService testUserService = testContainer.createUserService();
        
        System.out.println("\n🧪 Running with test configuration:");
        testUserService.registerUser("test_user", "test@example.com");
        testUserService.resetPassword("test_user", "test@example.com");
        
        // Verify mock email service
        MockEmailService mockEmailService = (MockEmailService) testContainer.get(EmailService.class);
        System.out.println("\n📊 Mock Email Service Report:");
        System.out.println("Emails sent: " + mockEmailService.getSentEmails().size());
        mockEmailService.getSentEmails().forEach(email -> System.out.println("  • " + email));
        
        System.out.println("\n🎯 Switching configurations on-the-fly:");
        
        // Create another container with different logger
        SimpleIoCContainer customContainer = new SimpleIoCContainer();
        customContainer.register(Logger.class, new FileLogger("custom.log"));
        customContainer.register(EmailService.class, new SMTPEmailService("smtp.custom.com"));
        customContainer.register(DatabaseService.class, new InMemoryDatabaseService());
        
        UserService customUserService = customContainer.createUserService();
        customUserService.registerUser("custom_user", "custom@example.com");
    }
    
    private static void showIoCBenefits() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                     IoC PATTERN BENEFITS");
        System.out.println("=".repeat(70));
        
        System.out.println("✓ LOOSE COUPLING:");
        System.out.println("  • Classes depend on abstractions, not concrete implementations");
        System.out.println("  • Easy to swap implementations without changing client code");
        
        System.out.println("\n✓ TESTABILITY:");
        System.out.println("  • Can inject mock objects for unit testing");
        System.out.println("  • Isolated testing of business logic");
        
        System.out.println("\n✓ CONFIGURATION FLEXIBILITY:");
        System.out.println("  • Different configurations for different environments");
        System.out.println("  • Runtime configuration changes possible");
        
        System.out.println("\n✓ SINGLE RESPONSIBILITY:");
        System.out.println("  • Classes focus on business logic, not dependency management");
        System.out.println("  • IoC container handles object lifecycle");
        
        System.out.println("\n✓ MAINTAINABILITY:");
        System.out.println("  • Changes in dependencies don't affect client code");
        System.out.println("  • Centralized dependency configuration");
        
        System.out.println("\n📋 COMMON IoC IMPLEMENTATIONS:");
        System.out.println("  • Spring Framework (Java)");
        System.out.println("  • Google Guice (Java)");
        System.out.println("  • Unity Container (.NET)");
        System.out.println("  • Autofac (.NET)");
        
        System.out.println("=".repeat(70));
    }
} 