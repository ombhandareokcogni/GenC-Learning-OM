package com.learning.aop.realworld;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * SecurityAspect.java
 * 
 * This aspect demonstrates how AOP can be used to implement security concerns
 * like authentication and authorization without cluttering business logic.
 * 
 * Security Features Demonstrated:
 * - Method-level authorization checks
 * - User authentication validation
 * - Role-based access control
 * - Security logging and audit trails
 * - Sensitive data protection
 * 
 * This shows how AOP can centralize security logic and apply it
 * consistently across the entire application.
 * 
 * @author Learning Journey
 * @version 1.0
 */
@Aspect
@Component
public class SecurityAspect {
    
    // Simulate current user context (in real app, this would come from Spring Security)
    private final ThreadLocal<User> currentUser = new ThreadLocal<>();
    
    /**
     * POINTCUT DEFINITIONS FOR SECURITY
     */
    
    // Methods that require authentication
    @Pointcut("execution(* *..*Service.*(..)) && !execution(* *..*Service.login(..))")
    public void authenticatedMethods() {}
    
    // Methods that modify data (require WRITE permission)
    @Pointcut("execution(* *..*Service.create*(..)) || " +
              "execution(* *..*Service.update*(..)) || " +
              "execution(* *..*Service.delete*(..))")
    public void dataModificationMethods() {}
    
    // Admin-only methods
    @Pointcut("execution(* *..*Service.*Admin*(..)) || " +
              "execution(* *..*Service.deleteUser(..))")
    public void adminOnlyMethods() {}
    
    // Methods that access sensitive data
    @Pointcut("execution(* *..*Service.getPassword(..)) || " +
              "execution(* *..*Service.getCreditCard(..))")
    public void sensitiveDataMethods() {}
    
    /**
     * AUTHENTICATION CHECK
     * 
     * Ensures user is logged in before accessing protected methods
     */
    @Before("authenticatedMethods()")
    public void checkAuthentication() {
        System.out.println("\n🔐 [SECURITY] === AUTHENTICATION CHECK ===");
        
        User user = getCurrentUser();
        if (user == null) {
            System.out.println("   ❌ Authentication failed: No user logged in");
            throw new SecurityException("Authentication required. Please log in.");
        }
        
        System.out.println("   ✅ Authentication successful: " + user.getUsername());
        System.out.println("   👤 User ID: " + user.getId());
        System.out.println("   🏷️  Role: " + user.getRole());
        System.out.println("   ════════════════════════════════════════");
    }
    
    /**
     * AUTHORIZATION CHECK FOR DATA MODIFICATION
     * 
     * Ensures user has WRITE permission for create/update/delete operations
     */
    @Around("dataModificationMethods()")
    public Object checkWritePermission(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("\n✏️  [SECURITY] === WRITE PERMISSION CHECK ===");
        System.out.println("   🔧 Method: " + methodName);
        
        User user = getCurrentUser();
        if (user == null) {
            throw new SecurityException("Authentication required for data modification");
        }
        
        if (!user.hasPermission(Permission.WRITE)) {
            System.out.println("   ❌ Authorization failed: User lacks WRITE permission");
            System.out.println("   👤 User: " + user.getUsername() + " (Role: " + user.getRole() + ")");
            throw new SecurityException("Insufficient permissions for data modification");
        }
        
        System.out.println("   ✅ Authorization successful: WRITE permission verified");
        System.out.println("   ▶️  Proceeding with method execution...");
        
        try {
            Object result = joinPoint.proceed();
            
            // Log successful data modification
            System.out.println("   ✅ Data modification completed successfully");
            logSecurityEvent("DATA_MODIFIED", user, methodName, "SUCCESS");
            
            return result;
            
        } catch (Throwable throwable) {
            // Log failed data modification
            System.out.println("   ❌ Data modification failed: " + throwable.getMessage());
            logSecurityEvent("DATA_MODIFICATION_FAILED", user, methodName, "FAILURE");
            throw throwable;
        }
    }
    
    /**
     * ADMIN AUTHORIZATION CHECK
     * 
     * Ensures only admin users can access admin-only methods
     */
    @Before("adminOnlyMethods()")
    public void checkAdminPermission() {
        System.out.println("\n👑 [SECURITY] === ADMIN PERMISSION CHECK ===");
        
        User user = getCurrentUser();
        if (user == null) {
            throw new SecurityException("Authentication required for admin operations");
        }
        
        if (!user.getRole().equals(Role.ADMIN)) {
            System.out.println("   ❌ Authorization failed: Admin role required");
            System.out.println("   👤 User: " + user.getUsername() + " (Role: " + user.getRole() + ")");
            logSecurityEvent("UNAUTHORIZED_ADMIN_ACCESS", user, "admin_method", "FAILURE");
            throw new SecurityException("Admin privileges required");
        }
        
        System.out.println("   ✅ Admin authorization successful");
        System.out.println("   👤 Admin User: " + user.getUsername());
        logSecurityEvent("ADMIN_ACCESS", user, "admin_method", "SUCCESS");
        System.out.println("   ════════════════════════════════════════");
    }
    
    /**
     * SENSITIVE DATA ACCESS PROTECTION
     * 
     * Adds extra logging and validation for sensitive data access
     */
    @Around("sensitiveDataMethods()")
    public Object protectSensitiveData(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        System.out.println("\n🔒 [SECURITY] === SENSITIVE DATA ACCESS ===");
        System.out.println("   🔧 Method: " + methodName);
        
        User user = getCurrentUser();
        if (user == null) {
            throw new SecurityException("Authentication required for sensitive data access");
        }
        
        // Extra validation for sensitive data
        if (!user.hasPermission(Permission.READ_SENSITIVE)) {
            System.out.println("   ❌ Access denied: Sensitive data permission required");
            logSecurityEvent("SENSITIVE_DATA_ACCESS_DENIED", user, methodName, "FAILURE");
            throw new SecurityException("Insufficient permissions for sensitive data");
        }
        
        // Log the access attempt
        logSecurityEvent("SENSITIVE_DATA_ACCESS", user, methodName, "ATTEMPT");
        
        try {
            Object result = joinPoint.proceed();
            
            // Mask sensitive data in logs
            System.out.println("   ✅ Sensitive data access granted");
            System.out.println("   🎯 Result: [SENSITIVE DATA - MASKED]");
            logSecurityEvent("SENSITIVE_DATA_ACCESS", user, methodName, "SUCCESS");
            
            return result;
            
        } catch (Throwable throwable) {
            logSecurityEvent("SENSITIVE_DATA_ACCESS", user, methodName, "ERROR");
            throw throwable;
        }
    }
    
    /**
     * SECURITY HELPER METHODS
     */
    
    private User getCurrentUser() {
        return currentUser.get();
    }
    
    public void setCurrentUser(User user) {
        currentUser.set(user);
        if (user != null) {
            System.out.println("🔑 [SECURITY] User logged in: " + user.getUsername() + 
                              " (Role: " + user.getRole() + ")");
        } else {
            System.out.println("🔑 [SECURITY] User logged out");
        }
    }
    
    private void logSecurityEvent(String eventType, User user, String method, String status) {
        String timestamp = java.time.LocalDateTime.now().toString();
        System.out.println("\n📋 [AUDIT LOG] Security Event Recorded:");
        System.out.println("   🕐 Timestamp: " + timestamp);
        System.out.println("   🏷️  Event: " + eventType);
        System.out.println("   👤 User: " + (user != null ? user.getUsername() : "ANONYMOUS"));
        System.out.println("   🔧 Method: " + method);
        System.out.println("   📊 Status: " + status);
        System.out.println("   ════════════════════════════════════════");
        
        // In a real application, this would write to:
        // - Database audit table
        // - Log files
        // - Security information and event management (SIEM) system
        // - Monitoring dashboard
    }
    
    /**
     * DEMO METHODS FOR TESTING
     */
    
    public void loginUser(String username, String password, Role role) {
        System.out.println("\n🔐 [LOGIN] Attempting login for: " + username);
        
        // Simulate login validation (in real app, check against database)
        if (username != null && password != null) {
            User user = new User(1L, username, role);
            setCurrentUser(user);
            System.out.println("✅ Login successful");
        } else {
            System.out.println("❌ Login failed: Invalid credentials");
        }
    }
    
    public void logout() {
        User user = getCurrentUser();
        if (user != null) {
            logSecurityEvent("LOGOUT", user, "logout", "SUCCESS");
        }
        setCurrentUser(null);
    }
    
    public void demonstrateSecurity() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔐 SECURITY ASPECT DEMONSTRATION");
        System.out.println("=".repeat(60));
        
        // Demonstrate different security scenarios
        System.out.println("\n1. Testing without authentication:");
        try {
            // This would trigger authentication check and fail
            System.out.println("Attempting to access protected method...");
            checkAuthentication();
        } catch (SecurityException e) {
            System.out.println("Expected security exception: " + e.getMessage());
        }
        
        System.out.println("\n2. Testing with regular user:");
        loginUser("john_doe", "password123", Role.USER);
        
        System.out.println("\n3. Testing admin access with regular user:");
        try {
            checkAdminPermission();
        } catch (SecurityException e) {
            System.out.println("Expected admin permission denial: " + e.getMessage());
        }
        
        System.out.println("\n4. Testing with admin user:");
        loginUser("admin_user", "admin_pass", Role.ADMIN);
        checkAdminPermission();
        
        logout();
        System.out.println("\n🎉 Security demonstration completed!");
    }
}

// Supporting classes for the security demo

class User {
    private Long id;
    private String username;
    private Role role;
    
    public User(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    
    public boolean hasPermission(Permission permission) {
        switch (role) {
            case ADMIN:
                return true; // Admin has all permissions
            case USER:
                return permission == Permission.READ || permission == Permission.WRITE;
            case GUEST:
                return permission == Permission.READ;
            default:
                return false;
        }
    }
    
    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
}

enum Role {
    ADMIN, USER, GUEST
}

enum Permission {
    READ, WRITE, READ_SENSITIVE, ADMIN
}

/**
 * REAL-WORLD SECURITY INTEGRATION:
 * 
 * In a production application, this aspect would integrate with:
 * 
 * 1. SPRING SECURITY:
 *    - Get current user from SecurityContextHolder
 *    - Use @PreAuthorize annotations with SpEL expressions
 *    - Integrate with OAuth2, JWT tokens
 * 
 * 2. METHOD SECURITY:
 *    @PreAuthorize("hasRole('ADMIN')")
 *    @PostAuthorize("returnObject.owner == authentication.name")
 *    @Secured("ROLE_USER")
 * 
 * 3. AUDIT LOGGING:
 *    - Write to dedicated audit database
 *    - Use structured logging (JSON format)
 *    - Integrate with SIEM systems
 * 
 * 4. COMPLIANCE:
 *    - GDPR data access logging
 *    - SOX compliance for financial data
 *    - HIPAA for healthcare applications
 * 
 * 5. MONITORING:
 *    - Failed authentication alerts
 *    - Unusual access pattern detection
 *    - Real-time security dashboards
 * 
 * This aspect demonstrates how AOP can provide comprehensive
 * security without polluting business logic!
 */ 