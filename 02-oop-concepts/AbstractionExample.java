package com.learning.oop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Abstraction Example using Spring
 * Demonstrates abstraction through abstract classes and interfaces
 * Shows how abstraction hides implementation details while exposing essential functionality
 */

// Abstract interface - highest level of abstraction
interface DataRepository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    boolean existsById(ID id);
    long count();
}

// Abstract class - provides partial implementation and defines template
abstract class BaseRepository<T, ID> implements DataRepository<T, ID> {
    protected final Map<ID, T> dataStore = new HashMap<>();
    
    // Template method - defines the algorithm structure
    @Override
    public T save(T entity) {
        validateEntity(entity);
        ID id = extractId(entity);
        
        if (id == null) {
            id = generateId();
            setId(entity, id);
        }
        
        T savedEntity = performSave(entity, id);
        afterSave(savedEntity);
        return savedEntity;
    }
    
    // Common implementation for all repositories
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dataStore.get(id));
    }
    
    @Override
    public List<T> findAll() {
        return new ArrayList<>(dataStore.values());
    }
    
    @Override
    public void deleteById(ID id) {
        if (existsById(id)) {
            dataStore.remove(id);
            afterDelete(id);
        }
    }
    
    @Override
    public boolean existsById(ID id) {
        return dataStore.containsKey(id);
    }
    
    @Override
    public long count() {
        return dataStore.size();
    }
    
    // Abstract methods - must be implemented by concrete classes
    protected abstract ID extractId(T entity);
    protected abstract void setId(T entity, ID id);
    protected abstract ID generateId();
    protected abstract void validateEntity(T entity);
    
    // Hook methods - can be overridden by subclasses
    protected T performSave(T entity, ID id) {
        dataStore.put(id, entity);
        return entity;
    }
    
    protected void afterSave(T entity) {
        // Default implementation - do nothing
        System.out.println("Entity saved: " + entity.getClass().getSimpleName());
    }
    
    protected void afterDelete(ID id) {
        // Default implementation - do nothing
        System.out.println("Entity deleted with ID: " + id);
    }
}

// Entity classes
class User {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    public User(String username, String email) {
        this();
        this.username = username;
        this.email = email;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', email='%s'}", id, username, email);
    }
}

class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    
    public Product() {}
    
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=%.2f, quantity=%d}", 
                           id, name, price, quantity);
    }
}

// Concrete repository implementations
@Repository
class UserRepository extends BaseRepository<User, Long> {
    private static Long idCounter = 1L;
    
    @Override
    protected Long extractId(User entity) {
        return entity.getId();
    }
    
    @Override
    protected void setId(User entity, Long id) {
        entity.setId(id);
    }
    
    @Override
    protected Long generateId() {
        return idCounter++;
    }
    
    @Override
    protected void validateEntity(User entity) {
        if (entity.getUsername() == null || entity.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (entity.getEmail() == null || !entity.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
    }
    
    @Override
    protected void afterSave(User user) {
        super.afterSave(user);
        System.out.println("User created at: " + user.getCreatedAt());
    }
    
    // Additional methods specific to UserRepository
    public List<User> findByUsername(String username) {
        return findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .collect(Collectors.toList());
    }
    
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}

@Repository
class ProductRepository extends BaseRepository<Product, String> {
    private static int idCounter = 1;
    
    @Override
    protected String extractId(Product entity) {
        return entity.getId();
    }
    
    @Override
    protected void setId(Product entity, String id) {
        entity.setId(id);
    }
    
    @Override
    protected String generateId() {
        return "PROD-" + String.format("%04d", idCounter++);
    }
    
    @Override
    protected void validateEntity(Product entity) {
        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (entity.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        if (entity.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
    }
    
    @Override
    protected void afterSave(Product product) {
        super.afterSave(product);
        System.out.println("Product inventory updated");
    }
    
    // Additional methods specific to ProductRepository
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        return findAll().stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    public List<Product> findLowStockProducts(int threshold) {
        return findAll().stream()
                .filter(product -> product.getQuantity() < threshold)
                .collect(Collectors.toList());
    }
}

// Service layer - uses abstraction to work with repositories
@Service
class DataService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    @Autowired
    public DataService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    
    // Method that works with abstract DataRepository interface
    public <T, ID> void demonstrateAbstractOperations(DataRepository<T, ID> repository, 
                                                       T sampleEntity, 
                                                       String entityType) {
        System.out.println("\n=== Working with " + entityType + " Repository ===");
        
        // Save entity
        T savedEntity = repository.save(sampleEntity);
        System.out.println("Saved: " + savedEntity);
        
        // Check count
        System.out.println("Total " + entityType + " count: " + repository.count());
        
        // Find all
        List<T> allEntities = repository.findAll();
        System.out.println("All " + entityType + "s: " + allEntities.size() + " found");
        
        allEntities.forEach(entity -> System.out.println("  - " + entity));
    }
    
    public void populateSampleData() {
        System.out.println("=== Populating Sample Data ===");
        
        // Create sample users
        User[] users = {
            new User("john_doe", "john@example.com"),
            new User("jane_smith", "jane@example.com"),
            new User("bob_wilson", "bob@example.com")
        };
        
        for (User user : users) {
            userRepository.save(user);
        }
        
        // Create sample products
        Product[] products = {
            new Product("Laptop", 999.99, 10),
            new Product("Mouse", 25.50, 50),
            new Product("Keyboard", 75.00, 30),
            new Product("Monitor", 299.99, 15)
        };
        
        for (Product product : products) {
            productRepository.save(product);
        }
        
        System.out.println("Sample data populated successfully!");
    }
    
    public void demonstrateAbstraction() {
        System.out.println("\n=== Demonstrating Abstraction ===");
        
        // Show polymorphic behavior - same interface, different implementations
        DataRepository<User, Long> userRepo = userRepository;
        DataRepository<Product, String> productRepo = productRepository;
        
        System.out.println("User Repository - Total users: " + userRepo.count());
        System.out.println("Product Repository - Total products: " + productRepo.count());
        
        // Demonstrate template method pattern from abstract class
        System.out.println("\nTesting validation through abstraction:");
        try {
            User invalidUser = new User("", "invalid-email");
            userRepository.save(invalidUser);
        } catch (IllegalArgumentException e) {
            System.out.println("User validation error: " + e.getMessage());
        }
        
        try {
            Product invalidProduct = new Product("", -10, -5);
            productRepository.save(invalidProduct);
        } catch (IllegalArgumentException e) {
            System.out.println("Product validation error: " + e.getMessage());
        }
        
        // Demonstrate specific implementations
        System.out.println("\nRepository-specific methods:");
        Optional<User> user = userRepository.findByEmail("john@example.com");
        user.ifPresent(u -> System.out.println("Found user by email: " + u));
        
        List<Product> affordableProducts = productRepository.findByPriceRange(20, 100);
        System.out.println("Affordable products: " + affordableProducts.size());
        
        List<Product> lowStockProducts = productRepository.findLowStockProducts(20);
        System.out.println("Low stock products: " + lowStockProducts.size());
    }
    
    public void showAbstractionBenefits() {
        System.out.println("\n=== Abstraction Benefits ===");
        System.out.println("• Interface abstraction: DataRepository<T, ID> defines contract");
        System.out.println("• Abstract class: BaseRepository provides common implementation");
        System.out.println("• Template method pattern: save() algorithm is standardized");
        System.out.println("• Implementation hiding: Client doesn't know storage details");
        System.out.println("• Polymorphism: Work with different repository types uniformly");
        System.out.println("• Extension points: Hook methods allow customization");
        System.out.println("• Validation abstraction: Each entity type has its own rules");
        System.out.println("• Spring integration: All abstractions are managed by container");
    }
}

@Configuration
@ComponentScan(basePackages = "com.learning.oop")
class AbstractionConfig {
    // Configuration for Spring context
}

public class AbstractionExample {
    public static void main(String[] args) {
        System.out.println("=== Spring Abstraction Example ===\n");
        
        // Create Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(AbstractionConfig.class);
        
        // Get the service bean from Spring container
        DataService dataService = context.getBean(DataService.class);
        
        // Populate sample data
        dataService.populateSampleData();
        
        // Demonstrate abstraction with polymorphic repository usage
        UserRepository userRepo = context.getBean(UserRepository.class);
        ProductRepository productRepo = context.getBean(ProductRepository.class);
        
        dataService.demonstrateAbstractOperations(
            userRepo, 
            new User("test_user", "test@example.com"), 
            "User"
        );
        
        dataService.demonstrateAbstractOperations(
            productRepo, 
            new Product("Test Product", 99.99, 5), 
            "Product"
        );
        
        // Show abstraction features
        dataService.demonstrateAbstraction();
        dataService.showAbstractionBenefits();
    }
} 