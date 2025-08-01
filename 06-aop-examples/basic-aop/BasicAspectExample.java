package com.learning.aop.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

/**
 * BasicAspectExample.java
 * 
 * This is a simplified demonstration of AOP (Aspect-Oriented Programming) concepts.
 * This demo shows how aspects automatically intercept method calls and add
 * cross-cutting functionality like logging, performance monitoring, etc.
 * 
 * What you'll see when you run this:
 * 1. Spring creates proxy objects for your service classes
 * 2. When methods are called, aspects are automatically triggered
 * 3. Multiple advice types execute in a specific order
 * 4. Business logic remains clean and focused
 * 
 * This demonstrates the power of AOP:
 * - Separation of concerns
 * - Reduced code duplication
 * - Centralized cross-cutting functionality
 * 
 * @author Learning Journey
 * @version 1.0
 */
public class BasicAspectExample {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("🎯 SPRING AOP DEMONSTRATION");
        System.out.println("=".repeat(60));
        System.out.println("This demo shows how AOP intercepts method calls");
        System.out.println("and adds cross-cutting functionality automatically.\n");
        
        // Create Spring Application Context with AOP enabled
        ApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        
        // Get the DemoService bean (this will be a proxy object, not the original)
        DemoService demoService = context.getBean(DemoService.class);
        
        // Show what type of object we got
        System.out.println("📋 PROXY INFORMATION:");
        System.out.println("Object class: " + demoService.getClass().getName());
        System.out.println("Is it a proxy? " + demoService.getClass().getName().contains("$"));
        System.out.println();
        
        // Now let's call methods and watch AOP in action!
        demonstrateBasicAOP(demoService);
        demonstrateExceptionHandling(demoService);
        demonstratePerformanceMonitoring(demoService);
        
        System.out.println("=".repeat(60));
        System.out.println("🎉 AOP DEMONSTRATION COMPLETED!");
        System.out.println("=".repeat(60));
        System.out.println("Key takeaways:");
        System.out.println("✅ AOP separates cross-cutting concerns from business logic");
        System.out.println("✅ Aspects run automatically without modifying service code");
        System.out.println("✅ Multiple advice types can be applied to the same method");
        System.out.println("✅ Aspects make code more maintainable and testable");
    }
    
    /**
     * Demonstrates basic AOP functionality with successful method calls
     */
    private static void demonstrateBasicAOP(DemoService demoService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🔥 DEMO 1: BASIC AOP FUNCTIONALITY");
        System.out.println("=".repeat(50));
        System.out.println("Watch how aspects automatically intercept method calls:");
        System.out.println();
        
        try {
            // This method call will trigger multiple aspects:
            // @Before, @Around, @After, @AfterReturning
            String result = demoService.findData(1L);
            
            System.out.println("\n📊 RESULT SUMMARY:");
            System.out.println("✅ Method executed successfully");
            System.out.println("📦 Returned: " + result);
            System.out.println("🎯 Notice how aspects added logging without changing business code!");
            
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates exception handling in AOP
     */
    private static void demonstrateExceptionHandling(DemoService demoService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("💥 DEMO 2: EXCEPTION HANDLING WITH AOP");
        System.out.println("=".repeat(50));
        System.out.println("Watch how @AfterThrowing advice handles exceptions:");
        System.out.println();
        
        try {
            // This will trigger an exception, showing @AfterThrowing advice
            demoService.findData(999L);
            
        } catch (Exception e) {
            System.out.println("\n📊 EXCEPTION SUMMARY:");
            System.out.println("❌ Method threw exception (as expected)");
            System.out.println("💬 Exception message: " + e.getMessage());
            System.out.println("🎯 Notice how @AfterThrowing advice logged the error automatically!");
        }
    }
    
    /**
     * Demonstrates performance monitoring and @Around advice
     */
    private static void demonstratePerformanceMonitoring(DemoService demoService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("⏱️ DEMO 3: PERFORMANCE MONITORING WITH @AROUND");
        System.out.println("=".repeat(50));
        System.out.println("Watch how @Around advice measures execution time:");
        System.out.println();
        
        try {
            // This method has @Around advice that measures performance
            String result = demoService.createData("Sample Data");
            
            System.out.println("\n📊 PERFORMANCE SUMMARY:");
            System.out.println("✅ Method executed with performance monitoring");
            System.out.println("📦 Created: " + result);
            System.out.println("🎯 @Around advice measured execution time automatically!");
            
        } catch (Exception e) {
            System.out.println("❌ Error creating data: " + e.getMessage());
        }
    }
}

/**
 * Simple demo service for AOP demonstration
 * This service contains the target methods that will be intercepted by aspects
 */
@Service
class DemoService {
    
    public String findData(Long id) {
        System.out.println("[BUSINESS] Looking for data with ID: " + id);
        
        // Simulate some processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        if (id == 999L) {
            throw new RuntimeException("Data not found with ID: " + id);
        }
        
        String result = "Data-" + id;
        System.out.println("[BUSINESS] Found data: " + result);
        return result;
    }
    
    public String createData(String data) {
        System.out.println("[BUSINESS] Creating data: " + data);
        
        // Simulate some processing time
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String result = "Created: " + data + " (ID: " + System.currentTimeMillis() + ")";
        System.out.println("[BUSINESS] Data created successfully");
        return result;
    }
    
    public boolean deleteData(Long id) {
        System.out.println("[BUSINESS] Deleting data with ID: " + id);
        
        if (id < 0) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }
        
        System.out.println("[BUSINESS] Data deleted successfully");
        return true;
    }
}

/**
 * HOW TO UNDERSTAND THE OUTPUT:
 * 
 * When you run this demo, you'll see output from different sources:
 * 
 * 1. [BUSINESS] messages - These come from the actual business logic
 * 2. [AOP-BEFORE] messages - These come from @Before advice
 * 3. [AOP-AFTER] messages - These come from @After advice
 * 4. [AOP-AFTER-RETURNING] messages - These come from @AfterReturning advice
 * 5. [AOP-AFTER-THROWING] messages - These come from @AfterThrowing advice
 * 6. [AOP-AROUND] messages - These come from @Around advice
 * 7. [AOP-QUERY] messages - These come from query-specific advice
 * 8. [AOP-VALIDATION] messages - These come from parameter validation advice
 * 
 * EXECUTION ORDER YOU'LL SEE:
 * 
 * For a successful method call:
 * 1. @Around (before part)
 * 2. @Before
 * 3. [BUSINESS] - actual method execution
 * 4. @AfterReturning
 * 5. @After
 * 6. @Around (after part)
 * 
 * For a method that throws exception:
 * 1. @Around (before part)
 * 2. @Before
 * 3. [BUSINESS] - method execution (throws exception)
 * 4. @AfterThrowing
 * 5. @After
 * 6. @Around (exception handling part)
 * 
 * WHAT THIS DEMONSTRATES:
 * 
 * ✅ SEPARATION OF CONCERNS:
 *    Business logic (DemoService) focuses only on data management
 *    Cross-cutting concerns (logging, monitoring) are handled by aspects
 * 
 * ✅ CODE REUSABILITY:
 *    The same logging aspect can be applied to any service class
 *    No need to duplicate logging code in every method
 * 
 * ✅ MAINTAINABILITY:
 *    Changes to logging behavior only require updating the aspect
 *    Business logic remains untouched
 * 
 * ✅ TESTABILITY:
 *    Business logic can be tested without aspects
 *    Aspects can be tested independently
 * 
 * TRY THIS:
 * 
 * 1. Run this demo as-is to see basic AOP functionality
 * 2. Comment out specific advice methods in LoggingAspect to see the difference
 * 3. Add new methods to DemoService and watch them get intercepted automatically
 * 4. Modify pointcut expressions to change which methods are intercepted
 * 5. Add new aspects for different concerns (security, caching, etc.)
 * 
 * NEXT STEPS:
 * 
 * - Explore the real-world-examples/ directory for practical AOP applications
 * - Study pointcut-expressions/ for advanced pointcut techniques
 * - Look at advice-types/ for deeper understanding of each advice type
 * - Check out the tests/ directory to see how to test AOP functionality
 */ 