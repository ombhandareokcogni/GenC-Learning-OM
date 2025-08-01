package com.learning.designpatterns.factory;

/**
 * Factory_Example1_Simple.java
 * 
 * This example demonstrates the Simple Factory Pattern (also known as Static Factory Method):
 * - Centralizes object creation logic
 * - Hides complex creation logic from clients
 * - Provides a single point of control for object creation
 * - Violates Open-Closed Principle (must modify factory for new types)
 * 
 * Real-world use cases:
 * - Database connection creation based on configuration
 * - UI component creation based on platform
 * - Payment processor selection based on payment method
 * 
 * @author Learning Journey
 * @version 1.0
 */
public class Factory_Example1_Simple {
    
    public static void main(String[] args) {
        System.out.println("=== Simple Factory Pattern Demonstration ===\n");
        
        demonstrateShapeFactory();
        demonstrateDatabaseFactory();
        demonstratePaymentFactory();
    }
    
    /**
     * Example 1: Shape Factory
     * Creates different geometric shapes based on type
     */
    private static void demonstrateShapeFactory() {
        System.out.println("1. SHAPE FACTORY EXAMPLE");
        System.out.println("=========================");
        
        // Using factory to create different shapes
        Shape circle = ShapeFactory.createShape("CIRCLE");
        Shape rectangle = ShapeFactory.createShape("RECTANGLE");
        Shape triangle = ShapeFactory.createShape("TRIANGLE");
        
        // Client code doesn't need to know about concrete classes
        circle.draw();
        rectangle.draw();
        triangle.draw();
        
        // Calculate areas
        System.out.println("Circle area: " + circle.calculateArea());
        System.out.println("Rectangle area: " + rectangle.calculateArea());
        System.out.println("Triangle area: " + triangle.calculateArea());
        
        System.out.println();
    }
    
    /**
     * Example 2: Database Connection Factory
     * Creates database connections based on database type
     */
    private static void demonstrateDatabaseFactory() {
        System.out.println("2. DATABASE CONNECTION FACTORY");
        System.out.println("===============================");
        
        // Create different database connections
        DatabaseConnection mysql = DatabaseConnectionFactory.createConnection("MYSQL");
        DatabaseConnection postgresql = DatabaseConnectionFactory.createConnection("POSTGRESQL");
        DatabaseConnection oracle = DatabaseConnectionFactory.createConnection("ORACLE");
        
        // Use connections
        mysql.connect();
        mysql.executeQuery("SELECT * FROM users");
        mysql.disconnect();
        
        postgresql.connect();
        postgresql.executeQuery("SELECT * FROM products");
        postgresql.disconnect();
        
        oracle.connect();
        oracle.executeQuery("SELECT * FROM orders");
        oracle.disconnect();
        
        System.out.println();
    }
    
    /**
     * Example 3: Payment Processor Factory
     * Creates payment processors based on payment method
     */
    private static void demonstratePaymentFactory() {
        System.out.println("3. PAYMENT PROCESSOR FACTORY");
        System.out.println("=============================");
        
        // Process different payment types
        PaymentProcessor creditCard = PaymentProcessorFactory.createProcessor("CREDIT_CARD");
        PaymentProcessor paypal = PaymentProcessorFactory.createProcessor("PAYPAL");
        PaymentProcessor bitcoin = PaymentProcessorFactory.createProcessor("BITCOIN");
        
        // Process payments
        creditCard.processPayment(100.00);
        paypal.processPayment(75.50);
        bitcoin.processPayment(200.00);
        
        System.out.println();
    }
}

// ============================================================================
// SHAPE FACTORY EXAMPLE
// ============================================================================

/**
 * Shape interface - defines common behavior for all shapes
 */
interface Shape {
    void draw();
    double calculateArea();
    String getShapeType();
}

/**
 * Concrete shape implementations
 */
class Circle implements Shape {
    private double radius = 5.0;
    
    @Override
    public void draw() {
        System.out.println("Drawing a Circle with radius: " + radius);
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public String getShapeType() {
        return "Circle";
    }
}

class Rectangle implements Shape {
    private double width = 10.0;
    private double height = 6.0;
    
    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle with width: " + width + ", height: " + height);
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public String getShapeType() {
        return "Rectangle";
    }
}

class Triangle implements Shape {
    private double base = 8.0;
    private double height = 4.0;
    
    @Override
    public void draw() {
        System.out.println("Drawing a Triangle with base: " + base + ", height: " + height);
    }
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
    
    @Override
    public String getShapeType() {
        return "Triangle";
    }
}

/**
 * Simple Factory for creating shapes
 * Centralizes object creation logic
 */
class ShapeFactory {
    
    /**
     * Creates shape objects based on type
     * This method encapsulates the object creation logic
     * 
     * @param shapeType the type of shape to create
     * @return Shape instance or null if type not supported
     */
    public static Shape createShape(String shapeType) {
        // Input validation
        if (shapeType == null || shapeType.trim().isEmpty()) {
            throw new IllegalArgumentException("Shape type cannot be null or empty");
        }
        
        // Normalize input
        shapeType = shapeType.toUpperCase().trim();
        
        // Factory creation logic
        switch (shapeType) {
            case "CIRCLE":
                System.out.println("Factory: Creating Circle instance");
                return new Circle();
            case "RECTANGLE":
                System.out.println("Factory: Creating Rectangle instance");
                return new Rectangle();
            case "TRIANGLE":
                System.out.println("Factory: Creating Triangle instance");
                return new Triangle();
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
    }
    
    /**
     * Returns list of supported shape types
     */
    public static String[] getSupportedShapes() {
        return new String[]{"CIRCLE", "RECTANGLE", "TRIANGLE"};
    }
}

// ============================================================================
// DATABASE CONNECTION FACTORY EXAMPLE
// ============================================================================

/**
 * Database connection interface
 */
interface DatabaseConnection {
    void connect();
    void disconnect();
    void executeQuery(String query);
    String getConnectionInfo();
}

/**
 * MySQL database connection implementation
 */
class MySQLConnection implements DatabaseConnection {
    private String host = "localhost:3306";
    private boolean connected = false;
    
    @Override
    public void connect() {
        System.out.println("Connecting to MySQL database at " + host);
        connected = true;
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from MySQL database");
        connected = false;
    }
    
    @Override
    public void executeQuery(String query) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("MySQL: Executing query - " + query);
    }
    
    @Override
    public String getConnectionInfo() {
        return "MySQL Connection to " + host;
    }
}

/**
 * PostgreSQL database connection implementation
 */
class PostgreSQLConnection implements DatabaseConnection {
    private String host = "localhost:5432";
    private boolean connected = false;
    
    @Override
    public void connect() {
        System.out.println("Connecting to PostgreSQL database at " + host);
        connected = true;
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from PostgreSQL database");
        connected = false;
    }
    
    @Override
    public void executeQuery(String query) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("PostgreSQL: Executing query - " + query);
    }
    
    @Override
    public String getConnectionInfo() {
        return "PostgreSQL Connection to " + host;
    }
}

/**
 * Oracle database connection implementation
 */
class OracleConnection implements DatabaseConnection {
    private String host = "localhost:1521";
    private boolean connected = false;
    
    @Override
    public void connect() {
        System.out.println("Connecting to Oracle database at " + host);
        connected = true;
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnecting from Oracle database");
        connected = false;
    }
    
    @Override
    public void executeQuery(String query) {
        if (!connected) {
            throw new IllegalStateException("Not connected to database");
        }
        System.out.println("Oracle: Executing query - " + query);
    }
    
    @Override
    public String getConnectionInfo() {
        return "Oracle Connection to " + host;
    }
}

/**
 * Database connection factory
 */
class DatabaseConnectionFactory {
    
    public static DatabaseConnection createConnection(String dbType) {
        if (dbType == null || dbType.trim().isEmpty()) {
            throw new IllegalArgumentException("Database type cannot be null or empty");
        }
        
        dbType = dbType.toUpperCase().trim();
        
        switch (dbType) {
            case "MYSQL":
                return new MySQLConnection();
            case "POSTGRESQL":
                return new PostgreSQLConnection();
            case "ORACLE":
                return new OracleConnection();
            default:
                throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
    }
}

// ============================================================================
// PAYMENT PROCESSOR FACTORY EXAMPLE
// ============================================================================

/**
 * Payment processor interface
 */
interface PaymentProcessor {
    void processPayment(double amount);
    String getProcessorName();
    boolean isPaymentSecure();
}

/**
 * Credit card payment processor
 */
class CreditCardProcessor implements PaymentProcessor {
    
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
        System.out.println("  - Validating card number");
        System.out.println("  - Checking credit limit");
        System.out.println("  - Charging card");
        System.out.println("  - Payment successful!");
    }
    
    @Override
    public String getProcessorName() {
        return "Credit Card Processor";
    }
    
    @Override
    public boolean isPaymentSecure() {
        return true;
    }
}

/**
 * PayPal payment processor
 */
class PayPalProcessor implements PaymentProcessor {
    
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        System.out.println("  - Redirecting to PayPal");
        System.out.println("  - User authentication");
        System.out.println("  - Confirming payment");
        System.out.println("  - Payment completed!");
    }
    
    @Override
    public String getProcessorName() {
        return "PayPal Processor";
    }
    
    @Override
    public boolean isPaymentSecure() {
        return true;
    }
}

/**
 * Bitcoin payment processor
 */
class BitcoinProcessor implements PaymentProcessor {
    
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Bitcoin payment of $" + amount);
        System.out.println("  - Generating payment address");
        System.out.println("  - Waiting for blockchain confirmation");
        System.out.println("  - Transaction confirmed");
        System.out.println("  - Payment successful!");
    }
    
    @Override
    public String getProcessorName() {
        return "Bitcoin Processor";
    }
    
    @Override
    public boolean isPaymentSecure() {
        return true;
    }
}

/**
 * Payment processor factory
 */
class PaymentProcessorFactory {
    
    public static PaymentProcessor createProcessor(String paymentType) {
        if (paymentType == null || paymentType.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment type cannot be null or empty");
        }
        
        paymentType = paymentType.toUpperCase().trim();
        
        switch (paymentType) {
            case "CREDIT_CARD":
                return new CreditCardProcessor();
            case "PAYPAL":
                return new PayPalProcessor();
            case "BITCOIN":
                return new BitcoinProcessor();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
} 