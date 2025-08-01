package com.learning.designpatterns.dependencyinjection;

import java.util.ArrayList;
import java.util.List;

/**
 * DI_Example1_Constructor.java
 * 
 * This example demonstrates Constructor Dependency Injection:
 * - Dependencies are provided through constructor parameters
 * - Ensures immutable dependencies (final fields)
 * - Forces explicit dependency declaration
 * - Prevents circular dependencies at compile time
 * - Enables easy unit testing with mock objects
 * 
 * Benefits:
 * - Immutable dependencies (thread-safe)
 * - Clear dependency requirements
 * - Fail-fast behavior (missing dependencies detected early)
 * - Easier testing and mocking
 * 
 * @author Learning Journey
 * @version 1.0
 */
public class DI_Example1_Constructor {
    
    public static void main(String[] args) {
        System.out.println("=== Constructor Dependency Injection Demonstration ===\n");
        
        demonstrateWithoutDI();
        demonstrateWithConstructorDI();
        demonstrateComplexDIScenario();
    }
    
    /**
     * Shows problems with tight coupling (without DI)
     */
    private static void demonstrateWithoutDI() {
        System.out.println("1. WITHOUT DEPENDENCY INJECTION (Tight Coupling)");
        System.out.println("=================================================");
        
        // Tightly coupled service - hard to test and maintain
        TightlyCoupledOrderService orderService = new TightlyCoupledOrderService();
        orderService.createOrder("ORD001", 150.00);
        
        System.out.println("Problems with tight coupling:");
        System.out.println("- Hard to unit test (cannot mock dependencies)");
        System.out.println("- Violates Single Responsibility Principle");
        System.out.println("- Difficult to change implementations");
        System.out.println("- Creates hidden dependencies");
        
        System.out.println();
    }
    
    /**
     * Demonstrates constructor dependency injection benefits
     */
    private static void demonstrateWithConstructorDI() {
        System.out.println("2. WITH CONSTRUCTOR DEPENDENCY INJECTION");
        System.out.println("=========================================");
        
        // Create dependencies
        EmailService emailService = new EmailServiceImpl();
        PaymentProcessor paymentProcessor = new CreditCardPaymentProcessor();
        OrderRepository orderRepository = new DatabaseOrderRepository();
        
        // Inject dependencies through constructor
        LooselyDecoupledOrderService orderService = new LooselyDecoupledOrderService(
            emailService, 
            paymentProcessor, 
            orderRepository
        );
        
        // Use the service
        orderService.createOrder("ORD002", 250.00, "customer@example.com");
        
        System.out.println("\nBenefits of Constructor DI:");
        System.out.println("- Explicit dependency declaration");
        System.out.println("- Immutable dependencies (thread-safe)");
        System.out.println("- Easy to test with mock objects");
        System.out.println("- Clear separation of concerns");
        System.out.println("- Compile-time dependency validation");
        
        System.out.println();
    }
    
    /**
     * Demonstrates complex DI scenario with multiple layers
     */
    private static void demonstrateComplexDIScenario() {
        System.out.println("3. COMPLEX DEPENDENCY INJECTION SCENARIO");
        System.out.println("=========================================");
        
        // Build dependency graph bottom-up
        
        // Data layer
        CustomerRepository customerRepo = new DatabaseCustomerRepository();
        ProductRepository productRepo = new DatabaseProductRepository();
        
        // Service layer dependencies
        ValidationService validationService = new OrderValidationService();
        DiscountService discountService = new PercentageDiscountService();
        
        // Business layer
        CustomerService customerService = new CustomerServiceImpl(customerRepo);
        ProductService productService = new ProductServiceImpl(productRepo);
        
        // Top-level service with complex dependencies
        AdvancedOrderService orderService = new AdvancedOrderService(
            customerService,
            productService,
            validationService,
            discountService
        );
        
        // Use the service
        Order order = orderService.processOrder("CUST001", "PROD001", 3);
        System.out.println("Processed order: " + order);
        
        System.out.println("\nComplex DI demonstrates:");
        System.out.println("- Layered architecture with clear dependencies");
        System.out.println("- Each component has single responsibility");
        System.out.println("- Easy to swap implementations");
        System.out.println("- Testable components in isolation");
        
        System.out.println();
    }
}

// ============================================================================
// EXAMPLE 1: TIGHTLY COUPLED (PROBLEMS)
// ============================================================================

/**
 * Tightly coupled order service - demonstrates problems without DI
 * This class directly creates its dependencies, making it hard to test and maintain
 */
class TightlyCoupledOrderService {
    
    // Dependencies are created internally - tight coupling!
    private EmailService emailService = new EmailServiceImpl();
    private PaymentProcessor paymentProcessor = new CreditCardPaymentProcessor();
    private OrderRepository orderRepository = new DatabaseOrderRepository();
    
    public void createOrder(String orderId, double amount) {
        System.out.println("Creating order (tightly coupled): " + orderId);
        
        // Process payment
        boolean paymentSuccess = paymentProcessor.processPayment(amount);
        
        if (paymentSuccess) {
            // Save order
            Order order = new Order(orderId, amount);
            orderRepository.save(order);
            
            // Send notification
            emailService.sendOrderConfirmation(orderId);
        }
    }
}

// ============================================================================
// EXAMPLE 2: CONSTRUCTOR DEPENDENCY INJECTION
// ============================================================================

/**
 * Loosely coupled order service using constructor dependency injection
 * Dependencies are injected through constructor, making it flexible and testable
 */
class LooselyDecoupledOrderService {
    
    // Dependencies are final - immutable and thread-safe
    private final EmailService emailService;
    private final PaymentProcessor paymentProcessor;
    private final OrderRepository orderRepository;
    
    /**
     * Constructor injection - all dependencies must be provided
     * This ensures the object is fully initialized and dependencies are explicit
     */
    public LooselyDecoupledOrderService(
            EmailService emailService,
            PaymentProcessor paymentProcessor,
            OrderRepository orderRepository) {
        
        // Validate dependencies (fail-fast principle)
        this.emailService = requireNonNull(emailService, "EmailService cannot be null");
        this.paymentProcessor = requireNonNull(paymentProcessor, "PaymentProcessor cannot be null");
        this.orderRepository = requireNonNull(orderRepository, "OrderRepository cannot be null");
    }
    
    public void createOrder(String orderId, double amount, String customerEmail) {
        System.out.println("Creating order (loosely coupled): " + orderId);
        
        try {
            // Process payment using injected dependency
            boolean paymentSuccess = paymentProcessor.processPayment(amount);
            
            if (paymentSuccess) {
                // Save order using injected repository
                Order order = new Order(orderId, amount);
                orderRepository.save(order);
                
                // Send notification using injected email service
                emailService.sendOrderConfirmation(orderId);
                
                System.out.println("Order created successfully: " + orderId);
            } else {
                System.out.println("Payment failed for order: " + orderId);
            }
        } catch (Exception e) {
            System.out.println("Error creating order: " + e.getMessage());
        }
    }
    
    // Utility method for null checking
    private static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
        return obj;
    }
}

// ============================================================================
// EXAMPLE 3: COMPLEX DEPENDENCY INJECTION
// ============================================================================

/**
 * Advanced order service with complex dependency graph
 * Demonstrates how DI scales with application complexity
 */
class AdvancedOrderService {
    
    private final CustomerService customerService;
    private final ProductService productService;
    private final ValidationService validationService;
    private final DiscountService discountService;
    
    public AdvancedOrderService(
            CustomerService customerService,
            ProductService productService,
            ValidationService validationService,
            DiscountService discountService) {
        
        this.customerService = customerService;
        this.productService = productService;
        this.validationService = validationService;
        this.discountService = discountService;
    }
    
    public Order processOrder(String customerId, String productId, int quantity) {
        System.out.println("Processing advanced order...");
        
        // Get customer information
        Customer customer = customerService.getCustomer(customerId);
        System.out.println("Customer: " + customer.getName());
        
        // Get product information
        Product product = productService.getProduct(productId);
        System.out.println("Product: " + product.getName() + " ($" + product.getPrice() + ")");
        
        // Validate order
        boolean isValid = validationService.validateOrder(customer, product, quantity);
        if (!isValid) {
            throw new IllegalStateException("Order validation failed");
        }
        
        // Calculate total with discount
        double baseTotal = product.getPrice() * quantity;
        double discount = discountService.calculateDiscount(customer, baseTotal);
        double finalTotal = baseTotal - discount;
        
        System.out.println("Base total: $" + baseTotal);
        System.out.println("Discount: $" + discount);
        System.out.println("Final total: $" + finalTotal);
        
        // Create order
        String orderId = "ORD-" + System.currentTimeMillis();
        return new Order(orderId, finalTotal);
    }
}

// ============================================================================
// DOMAIN OBJECTS
// ============================================================================

/**
 * Order domain object
 */
class Order {
    private String orderId;
    private double amount;
    private long timestamp;
    
    public Order(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getOrderId() { return orderId; }
    public double getAmount() { return amount; }
    public long getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return "Order{id='" + orderId + "', amount=" + amount + "}";
    }
}

/**
 * Customer domain object
 */
class Customer {
    private String customerId;
    private String name;
    private String email;
    private boolean isPremium;
    
    public Customer(String customerId, String name, String email, boolean isPremium) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.isPremium = isPremium;
    }
    
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isPremium() { return isPremium; }
}

/**
 * Product domain object
 */
class Product {
    private String productId;
    private String name;
    private double price;
    private int stockQuantity;
    
    public Product(String productId, String name, double price, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
}

// ============================================================================
// SERVICE INTERFACES
// ============================================================================

interface EmailService {
    void sendOrderConfirmation(String orderId);
}

interface PaymentProcessor {
    boolean processPayment(double amount);
}

interface OrderRepository {
    void save(Order order);
    Order findById(String orderId);
}

interface CustomerService {
    Customer getCustomer(String customerId);
}

interface ProductService {
    Product getProduct(String productId);
}

interface ValidationService {
    boolean validateOrder(Customer customer, Product product, int quantity);
}

interface DiscountService {
    double calculateDiscount(Customer customer, double orderTotal);
}

interface CustomerRepository {
    Customer findById(String customerId);
}

interface ProductRepository {
    Product findById(String productId);
}

// ============================================================================
// SERVICE IMPLEMENTATIONS
// ============================================================================

class EmailServiceImpl implements EmailService {
    @Override
    public void sendOrderConfirmation(String orderId) {
        System.out.println("Email: Order confirmation sent for " + orderId);
    }
}

class CreditCardPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Payment: Processing $" + amount + " via credit card");
        return amount > 0 && amount < 10000; // Simulate validation
    }
}

class DatabaseOrderRepository implements OrderRepository {
    private List<Order> orders = new ArrayList<>();
    
    @Override
    public void save(Order order) {
        orders.add(order);
        System.out.println("Repository: Order saved to database - " + order.getOrderId());
    }
    
    @Override
    public Order findById(String orderId) {
        return orders.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}

class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public Customer getCustomer(String customerId) {
        return customerRepository.findById(customerId);
    }
}

class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public Product getProduct(String productId) {
        return productRepository.findById(productId);
    }
}

class OrderValidationService implements ValidationService {
    @Override
    public boolean validateOrder(Customer customer, Product product, int quantity) {
        System.out.println("Validation: Checking order validity");
        return customer != null && product != null && 
               quantity > 0 && quantity <= product.getStockQuantity();
    }
}

class PercentageDiscountService implements DiscountService {
    @Override
    public double calculateDiscount(Customer customer, double orderTotal) {
        double discountRate = customer.isPremium() ? 0.10 : 0.05; // 10% or 5%
        return orderTotal * discountRate;
    }
}

class DatabaseCustomerRepository implements CustomerRepository {
    @Override
    public Customer findById(String customerId) {
        // Simulate database lookup
        return new Customer(customerId, "John Doe", "john@example.com", true);
    }
}

class DatabaseProductRepository implements ProductRepository {
    @Override
    public Product findById(String productId) {
        // Simulate database lookup
        return new Product(productId, "Laptop", 999.99, 50);
    }
} 