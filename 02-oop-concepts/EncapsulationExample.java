package com.learning.oop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Encapsulation Example using Spring
 * Demonstrates data hiding and controlled access through getters/setters
 */
@Component
class User {
    // Private fields - demonstrating encapsulation
    private String username;
    private String email;
    private int age;
    
    @Value("${user.default.role:USER}")
    private String role;
    
    // Default constructor
    public User() {}
    
    // Parameterized constructor
    public User(String username, String email, int age) {
        this.username = username;
        this.email = email;
        setAge(age); // Using setter for validation
    }
    
    // Getter methods - controlled access to private data
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getRole() {
        return role;
    }
    
    // Setter methods with validation - controlled modification
    public void setUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = username.trim();
        } else {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }
    
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email.toLowerCase();
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age must be between 0 and 150");
        }
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    // Business method that uses encapsulated data
    public boolean isAdult() {
        return age >= 18;
    }
    
    public String getDisplayName() {
        return username + " (" + email + ")";
    }
    
    @Override
    public String toString() {
        return String.format("User{username='%s', email='%s', age=%d, role='%s'}", 
                           username, email, age, role);
    }
}

@Component
class UserManager {
    private final User user;
    
    // Constructor injection - another form of encapsulation
    public UserManager(User user) {
        this.user = user;
    }
    
    public void createUser(String username, String email, int age) {
        user.setUsername(username);
        user.setEmail(email);
        user.setAge(age);
        System.out.println("User created: " + user);
    }
    
    public void updateUserRole(String newRole) {
        user.setRole(newRole);
        System.out.println("User role updated: " + user.getRole());
    }
    
    public void displayUserInfo() {
        System.out.println("=== User Information ===");
        System.out.println("Display Name: " + user.getDisplayName());
        System.out.println("Adult Status: " + (user.isAdult() ? "Yes" : "No"));
        System.out.println("Full Details: " + user);
    }
}

@Configuration
@ComponentScan(basePackages = "com.learning.oop")
class EncapsulationConfig {
    // Configuration class for Spring context
}

public class EncapsulationExample {
    public static void main(String[] args) {
        System.out.println("=== Spring Encapsulation Example ===\n");
        
        // Create Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(EncapsulationConfig.class);
        
        // Get beans from Spring container
        UserManager userManager = context.getBean(UserManager.class);
        
        try {
            // Demonstrate encapsulation through controlled access
            System.out.println("1. Creating user with valid data:");
            userManager.createUser("john_doe", "john@example.com", 25);
            userManager.displayUserInfo();
            
            System.out.println("\n2. Updating user role:");
            userManager.updateUserRole("ADMIN");
            
            System.out.println("\n3. Attempting to create user with invalid data:");
            userManager.createUser("", "invalid-email", -5);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Key Encapsulation Benefits Demonstrated ===");
        System.out.println("• Private fields prevent direct access");
        System.out.println("• Getter methods provide controlled read access");
        System.out.println("• Setter methods enforce validation rules");
        System.out.println("• Business logic is encapsulated within the class");
        System.out.println("• Spring manages object lifecycle and dependencies");
    }
} 