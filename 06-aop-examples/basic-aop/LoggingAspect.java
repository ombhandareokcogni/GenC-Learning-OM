package com.learning.aop.basic;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect.java
 * 
 * This is a simple but comprehensive logging aspect that demonstrates
 * all the basic AOP advice types with clear, understandable examples.
 * 
 * This aspect will automatically log method calls in our UserService
 * without modifying the business logic code.
 * 
 * Key AOP Concepts Demonstrated:
 * - @Aspect: Marks this class as an aspect
 * - @Pointcut: Defines reusable pointcut expressions
 * - @Before: Executes before target method
 * - @After: Executes after target method (like finally block)
 * - @AfterReturning: Executes after successful method completion
 * - @AfterThrowing: Executes when method throws exception
 * - @Around: Wraps around method execution (most powerful)
 * 
 * @author Learning Journey
 * @version 1.0
 */
@Aspect      // This annotation tells Spring this is an AOP aspect
@Component   // This makes it a Spring bean so it can be managed by IoC container
public class LoggingAspect {
    
    /*
     * POINTCUT EXPRESSIONS EXPLAINED:
     * 
     * Pointcut expressions define WHERE advice should be applied.
     * They use a special syntax to match methods, classes, packages, etc.
     * 
     * Basic syntax: execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern) throws-pattern?)
     * 
     * Examples:
     * - execution(* *(..))                    - Any method with any parameters
     * - execution(public * *(..))             - Any public method
     * - execution(* com.learning.service.*.*(..)) - Any method in service package
     * - execution(* *Service.*(..))           - Any method in classes ending with "Service"
     * - execution(User *(..))                 - Any method returning User object
     * - execution(* *(String))                - Any method taking one String parameter
     * - execution(* *(String, ..))            - Any method with first parameter as String
     */
    
    /**
     * REUSABLE POINTCUT DEFINITIONS
     * 
     * @Pointcut annotation allows us to define reusable pointcut expressions
     * that can be referenced by multiple advice methods.
     * This makes code more maintainable and avoids duplication.
     */
    
    // Match any method in DemoService class
    @Pointcut("execution(* com.learning.aop.basic.DemoService.*(..))")
    public void demoServiceMethods() {
        // This method body is empty - it's just a placeholder for the pointcut expression
        // The method name (demoServiceMethods) can be referenced in advice annotations
    }
    
    // Match only methods that start with "create"
    @Pointcut("execution(* com.learning.aop.basic.DemoService.create*(..))")
    public void dataCreationMethods() {}
    
    // Match only methods that start with "find" or "get"
    @Pointcut("execution(* com.learning.aop.basic.DemoService.find*(..)) || " +
              "execution(* com.learning.aop.basic.DemoService.get*(..))")
    public void dataQueryMethods() {}
    
    // Match methods that take a Long parameter as first argument
    @Pointcut("execution(* com.learning.aop.basic.DemoService.*(Long, ..))")
    public void methodsWithLongParameter() {}
    
    /**
     * @BEFORE ADVICE
     * 
     * Executes BEFORE the target method is called.
     * Use cases:
     * - Input validation
     * - Security checks
     * - Logging method entry
     * - Setting up resources
     * 
     * JoinPoint provides access to:
     * - Method signature
     * - Method arguments
     * - Target object
     * - Proxy object
     */
    @Before("demoServiceMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        System.out.println("\n🚀 [AOP-BEFORE] === ENTERING METHOD ===");
        System.out.println("   📍 Class: " + className);
        System.out.println("   🔧 Method: " + methodName);
        System.out.println("   📝 Arguments: " + Arrays.toString(args));
        System.out.println("   ⏰ Time: " + java.time.LocalTime.now());
        System.out.println("   ════════════════════════════════════════");
    }
    
    /**
     * @AFTER ADVICE
     * 
     * Executes AFTER the target method completes (like finally block).
     * This runs regardless of whether the method:
     * - Completes successfully
     * - Throws an exception
     * - Returns normally
     * 
     * Use cases:
     * - Cleanup operations
     * - Resource release
     * - Logging method exit
     * - Audit trails
     */
    @After("demoServiceMethods()")
    public void logMethodExit(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        System.out.println("\n🏁 [AOP-AFTER] === EXITING METHOD ===");
        System.out.println("   📍 Class: " + className);
        System.out.println("   🔧 Method: " + methodName);
        System.out.println("   ⏰ Time: " + java.time.LocalTime.now());
        System.out.println("   💡 Status: Method execution completed");
        System.out.println("   ════════════════════════════════════════");
    }
    
    /**
     * @AFTERRETURNING ADVICE
     * 
     * Executes ONLY when the target method completes successfully
     * and returns a value (no exceptions thrown).
     * 
     * The 'returning' parameter allows you to access the return value.
     * The parameter name must match the method parameter name.
     * 
     * Use cases:
     * - Success logging
     * - Result validation
     * - Cache updates
     * - Success notifications
     */
    @AfterReturning(pointcut = "demoServiceMethods()", returning = "result")
    public void logMethodSuccess(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        System.out.println("\n✅ [AOP-AFTER-RETURNING] === SUCCESS ===");
        System.out.println("   📍 Class: " + className);
        System.out.println("   🔧 Method: " + methodName);
        System.out.println("   🎯 Return Value: " + result);
        System.out.println("   🎉 Status: SUCCESS - Method completed without errors");
        System.out.println("   ⏰ Time: " + java.time.LocalTime.now());
        System.out.println("   ════════════════════════════════════════");
    }
    
    /**
     * @AFTERTHROWING ADVICE
     * 
     * Executes ONLY when the target method throws an exception.
     * The 'throwing' parameter allows you to access the exception.
     * 
     * You can specify the exception type to catch only specific exceptions:
     * @AfterThrowing(pointcut = "userServiceMethods()", throwing = "ex")
     * public void handleException(JoinPoint joinPoint, RuntimeException ex)
     * 
     * Use cases:
     * - Error logging
     * - Error notifications
     * - Cleanup after errors
     * - Exception transformation
     */
    @AfterThrowing(pointcut = "demoServiceMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        System.out.println("\n❌ [AOP-AFTER-THROWING] === ERROR ===");
        System.out.println("   📍 Class: " + className);
        System.out.println("   🔧 Method: " + methodName);
        System.out.println("   📝 Arguments: " + Arrays.toString(args));
        System.out.println("   💥 Exception Type: " + exception.getClass().getSimpleName());
        System.out.println("   💬 Exception Message: " + exception.getMessage());
        System.out.println("   ⏰ Time: " + java.time.LocalTime.now());
        System.out.println("   ════════════════════════════════════════");
    }
    
    /**
     * @AROUND ADVICE (Most Powerful)
     * 
     * This is the most powerful advice type. It can:
     * - Execute code before AND after the method
     * - Control whether the method is called at all
     * - Modify the method arguments
     * - Modify the return value
     * - Handle exceptions
     * - Measure execution time
     * 
     * IMPORTANT: You MUST call joinPoint.proceed() to execute the target method.
     * If you don't call proceed(), the target method will never execute!
     * 
     * Use cases:
     * - Performance monitoring
     * - Caching (check cache before calling method)
     * - Security (authentication/authorization)
     * - Transaction management
     * - Retry logic
     */
    @Around("dataCreationMethods()")
    public Object monitorPerformanceAndSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        System.out.println("\n🔄 [AOP-AROUND] === WRAPPING METHOD ===");
        System.out.println("   📍 Class: " + className);
        System.out.println("   🔧 Method: " + methodName);
        System.out.println("   📝 Arguments: " + Arrays.toString(args));
        
        // BEFORE the method execution
        long startTime = System.currentTimeMillis();
        System.out.println("   ⏱️  Starting execution at: " + java.time.LocalTime.now());
        
        // Security check (example)
        if (args.length > 0 && args[0] instanceof String) {
            String name = (String) args[0];
            if (name.toLowerCase().contains("admin")) {
                System.out.println("   🔒 Security Check: Admin user creation detected");
            }
        }
        
        Object result = null;
        try {
            // ✨ THIS IS WHERE THE ACTUAL METHOD GETS CALLED ✨
            result = joinPoint.proceed();
            
            // Code here executes if method succeeds
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            System.out.println("   ✅ Method executed successfully");
            System.out.println("   ⏱️  Execution time: " + executionTime + " ms");
            System.out.println("   🎯 Result: " + result);
            
        } catch (Throwable throwable) {
            // Code here executes if method throws exception
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            System.out.println("   ❌ Method threw exception");
            System.out.println("   ⏱️  Execution time before error: " + executionTime + " ms");
            System.out.println("   💥 Exception: " + throwable.getMessage());
            
            // You can choose to:
            // 1. Re-throw the exception: throw throwable;
            // 2. Return a default value: return new User(-1L, "Default", "default@example.com");
            // 3. Transform the exception: throw new CustomException("Wrapped: " + throwable.getMessage());
            
            throw throwable; // Re-throw the original exception
        }
        
        System.out.println("   🏁 Around advice completed");
        System.out.println("   ════════════════════════════════════════");
        
        return result; // Return the result (can be modified if needed)
    }
    
    /**
     * CONDITIONAL ADVICE EXAMPLE
     * 
     * This shows how to apply advice only to specific methods
     * based on method names or parameters.
     */
    @Before("dataQueryMethods()")
    public void logQueryMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        
        System.out.println("\n🔍 [AOP-QUERY] Executing query method: " + methodName);
        
        // Special handling for expensive operations
        if (methodName.equals("findData")) {
            System.out.println("   ⚠️  Note: This is a data retrieval operation");
        }
    }
    
    /**
     * PARAMETER-BASED ADVICE EXAMPLE
     * 
     * This shows how to apply advice based on method parameters.
     */
    @Before("methodsWithLongParameter() && args(id, ..)")
    public void validateIdParameter(JoinPoint joinPoint, Long id) {
        String methodName = joinPoint.getSignature().getName();
        
        System.out.println("\n🔢 [AOP-VALIDATION] Validating ID parameter in " + methodName);
        
        if (id == null) {
            System.out.println("   ⚠️  Warning: ID parameter is null");
        } else if (id <= 0) {
            System.out.println("   ⚠️  Warning: ID parameter is invalid (must be positive): " + id);
        } else {
            System.out.println("   ✅ ID parameter is valid: " + id);
        }
    }
}

/**
 * IMPORTANT NOTES ABOUT THIS ASPECT:
 * 
 * 1. EXECUTION ORDER:
 *    When multiple advice apply to the same method, the order is:
 *    @Around (before part) → @Before → Target Method → @AfterReturning/@AfterThrowing → @After → @Around (after part)
 * 
 * 2. PERFORMANCE CONSIDERATIONS:
 *    - AOP adds overhead to method calls
 *    - Use aspects for cross-cutting concerns only
 *    - Avoid heavy computation in advice methods
 * 
 * 3. DEBUGGING TIPS:
 *    - Add System.out.println statements to verify advice execution
 *    - Use IDE debugging to step through advice code
 *    - Check Spring logs for AOP proxy creation
 * 
 * 4. COMMON PITFALLS:
 *    - Forgetting to call joinPoint.proceed() in @Around advice
 *    - Self-invocation doesn't trigger AOP (calling method within same class)
 *    - Final methods cannot be proxied with CGLIB
 *    - Private methods cannot be proxied
 * 
 * 5. BEST PRACTICES:
 *    - Keep advice methods simple and focused
 *    - Use meaningful pointcut names
 *    - Handle exceptions properly in @Around advice
 *    - Consider performance impact of advice execution
 *    - Write tests for your aspects
 */ 