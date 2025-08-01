package com.learning.aop.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * UserService.java
 * 
 * This is a simple business service class that demonstrates typical operations
 * that might need cross-cutting concerns like logging, security, or performance monitoring.
 * 
 * This class will be the TARGET for our AOP aspects.
 * When AOP is enabled, Spring will create a proxy around this class
 * and intercept method calls to apply aspects.
 * 
 * @author Learning Journey
 * @version 1.0
 */
@Service  // This makes it a Spring-managed bean that can be proxied by AOP
public class UserService {
    
    // Simulate a simple in-memory database
    private Map<Long, User> userDatabase = new HashMap<>();
    private Long nextId = 1L;
    
    // Initialize with some test data
    public UserService() {
        // Add some sample users
        createUser("John Doe", "john@example.com");
        createUser("Jane Smith", "jane@example.com");
        createUser("Bob Wilson", "bob@example.com");
    }
    
    /**
     * Creates a new user
     * This method will be intercepted by our AOP aspects for:
     * - Logging (to track user creation)
     * - Performance monitoring (to measure execution time)
     * - Security (to check if user has permission to create users)
     */
    public User createUser(String name, String email) {
        System.out.println("[BUSINESS] Creating user: " + name);
        
        // Simulate some business logic
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }
        
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        
        // Create the user
        User user = new User(nextId++, name, email);
        userDatabase.put(user.getId(), user);
        
        System.out.println("[BUSINESS] User created successfully with ID: " + user.getId());
        return user;
    }
    
    /**
     * Finds a user by ID
     * This method demonstrates:
     * - Method parameters in pointcut expressions
     * - Return value handling in aspects
     * - Exception handling when user not found
     */
    public User findUserById(Long id) {
        System.out.println("[BUSINESS] Looking for user with ID: " + id);
        
        // Simulate database lookup time
        try {
            Thread.sleep(100); // Simulate network/database delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        User user = userDatabase.get(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        
        System.out.println("[BUSINESS] Found user: " + user.getName());
        return user;
    }
    
    /**
     * Updates an existing user
     * This method will demonstrate:
     * - Multiple parameters in AOP advice
     * - Conditional advice execution
     * - Method signature matching in pointcuts
     */
    public User updateUser(Long id, String newName, String newEmail) {
        System.out.println("[BUSINESS] Updating user ID: " + id);
        
        User existingUser = findUserById(id); // This will also trigger AOP
        
        // Validate new data
        if (newName != null && !newName.trim().isEmpty()) {
            existingUser.setName(newName);
        }
        
        if (newEmail != null && newEmail.contains("@")) {
            existingUser.setEmail(newEmail);
        }
        
        System.out.println("[BUSINESS] User updated successfully");
        return existingUser;
    }
    
    /**
     * Deletes a user
     * This method will demonstrate:
     * - Security aspects (checking permissions)
     * - Audit logging (tracking deletions)
     * - Exception advice (handling errors)
     */
    public boolean deleteUser(Long id) {
        System.out.println("[BUSINESS] Attempting to delete user ID: " + id);
        
        if (!userDatabase.containsKey(id)) {
            throw new UserNotFoundException("Cannot delete - user not found with ID: " + id);
        }
        
        userDatabase.remove(id);
        System.out.println("[BUSINESS] User deleted successfully");
        return true;
    }
    
    /**
     * Gets all users
     * This method demonstrates:
     * - Collection return types in AOP
     * - Performance monitoring for potentially expensive operations
     * - Caching aspects
     */
    public List<User> getAllUsers() {
        System.out.println("[BUSINESS] Retrieving all users");
        
        // Simulate expensive database operation
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        List<User> users = new ArrayList<>(userDatabase.values());
        System.out.println("[BUSINESS] Retrieved " + users.size() + " users");
        return users;
    }
    
    /**
     * Searches users by name
     * This method demonstrates:
     * - String parameter handling in AOP
     * - Conditional aspect execution based on parameters
     * - Method naming patterns in pointcuts
     */
    public List<User> searchUsersByName(String namePattern) {
        System.out.println("[BUSINESS] Searching users with name pattern: " + namePattern);
        
        if (namePattern == null || namePattern.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<User> matchingUsers = new ArrayList<>();
        for (User user : userDatabase.values()) {
            if (user.getName().toLowerCase().contains(namePattern.toLowerCase())) {
                matchingUsers.add(user);
            }
        }
        
        System.out.println("[BUSINESS] Found " + matchingUsers.size() + " matching users");
        return matchingUsers;
    }
    
    /**
     * This method demonstrates a method that throws an exception
     * Used to show @AfterThrowing advice
     */
    public User getRiskyUser(Long id) {
        System.out.println("[BUSINESS] Getting risky user: " + id);
        
        if (id < 0) {
            throw new IllegalArgumentException("User ID cannot be negative");
        }
        
        if (id == 999L) {
            throw new RuntimeException("Simulated system error for user 999");
        }
        
        return findUserById(id);
    }
    
    /**
     * This method simulates a long-running operation
     * Perfect for performance monitoring aspects
     */
    public String performExpensiveOperation() {
        System.out.println("[BUSINESS] Starting expensive operation...");
        
        try {
            // Simulate complex computation
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Operation interrupted";
        }
        
        System.out.println("[BUSINESS] Expensive operation completed");
        return "Operation successful";
    }
}

/**
 * Simple User domain object
 */
class User {
    private Long id;
    private String name;
    private String email;
    private Date createdAt;
    
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = new Date();
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}

/**
 * Custom exception for user operations
 */
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
} 