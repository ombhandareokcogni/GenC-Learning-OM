package com.learning.aop.realworld;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * PerformanceMonitoringAspect.java
 * 
 * This aspect demonstrates real-world performance monitoring using AOP.
 * It automatically measures execution time, tracks method call statistics,
 * and provides performance insights without modifying business code.
 * 
 * Features demonstrated:
 * - Execution time measurement
 * - Method call counting
 * - Performance statistics tracking
 * - Slow method detection and alerts
 * - Memory usage monitoring
 * 
 * This is a practical example of how AOP can add enterprise-level
 * monitoring capabilities to any application.
 * 
 * @author Learning Journey
 * @version 1.0
 */
@Aspect
@Component
public class PerformanceMonitoringAspect {
    
    // Thread-safe collections to store performance metrics
    private final ConcurrentHashMap<String, MethodStats> methodStatistics = new ConcurrentHashMap<>();
    private final AtomicLong totalMethodCalls = new AtomicLong(0);
    
    // Configuration constants
    private static final long SLOW_METHOD_THRESHOLD_MS = 1000; // 1 second
    private static final long VERY_SLOW_METHOD_THRESHOLD_MS = 5000; // 5 seconds
    
    /**
     * POINTCUT DEFINITIONS
     * 
     * These define where performance monitoring should be applied.
     * You can customize these to monitor specific packages, classes, or methods.
     */
    
    // Monitor all service layer methods
    @Pointcut("execution(* *..*Service.*(..))")
    public void serviceMethods() {}
    
    // Monitor all repository layer methods
    @Pointcut("execution(* *..*Repository.*(..))")
    public void repositoryMethods() {}
    
    // Monitor methods annotated with @Monitored (custom annotation)
    @Pointcut("@annotation(com.learning.aop.annotations.Monitored)")
    public void monitoredMethods() {}
    
    // Combine multiple pointcuts - monitor service and repository methods
    @Pointcut("serviceMethods() || repositoryMethods()")
    public void businessMethods() {}
    
    /**
     * MAIN PERFORMANCE MONITORING ADVICE
     * 
     * This @Around advice wraps method execution to measure performance.
     * It demonstrates the most comprehensive monitoring approach.
     */
    @Around("businessMethods()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get method information
        String methodName = getMethodName(joinPoint);
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String fullMethodName = className + "." + methodName;
        
        // Track total method calls
        long callNumber = totalMethodCalls.incrementAndGet();
        
        // Print method entry with call number
        System.out.println("\n📊 [PERFORMANCE] === MONITORING CALL #" + callNumber + " ===");
        System.out.println("   🎯 Method: " + fullMethodName);
        System.out.println("   📝 Arguments: " + getArgumentsString(joinPoint.getArgs()));
        
        // Record start time and memory
        long startTime = System.currentTimeMillis();
        long startNano = System.nanoTime();
        long startMemory = getUsedMemory();
        
        Object result = null;
        Throwable thrownException = null;
        
        try {
            // Execute the actual method
            result = joinPoint.proceed();
            
        } catch (Throwable throwable) {
            thrownException = throwable;
            throw throwable; // Re-throw the exception
            
        } finally {
            // Calculate performance metrics
            long endTime = System.currentTimeMillis();
            long endNano = System.nanoTime();
            long endMemory = getUsedMemory();
            
            long executionTimeMs = endTime - startTime;
            long executionTimeNano = endNano - startNano;
            long memoryUsed = endMemory - startMemory;
            
            // Update statistics
            updateMethodStatistics(fullMethodName, executionTimeMs, thrownException == null);
            
            // Print performance results
            printPerformanceResults(
                fullMethodName, 
                executionTimeMs, 
                executionTimeNano, 
                memoryUsed, 
                result, 
                thrownException
            );
            
            // Check for performance issues
            checkPerformanceIssues(fullMethodName, executionTimeMs);
        }
        
        return result;
    }
    
    /**
     * SIMPLE EXECUTION TIME MONITORING
     * 
     * This is a simpler version that just measures execution time.
     * Use this when you only need basic timing information.
     */
    @Around("monitoredMethods()")
    public Object simpleTimeMonitoring(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = getMethodName(joinPoint);
        
        long startTime = System.currentTimeMillis();
        System.out.println("⏱️  [TIMER] Starting: " + methodName);
        
        try {
            Object result = joinPoint.proceed();
            
            long executionTime = System.currentTimeMillis() - startTime;
            System.out.println("⏱️  [TIMER] Completed: " + methodName + " in " + executionTime + "ms");
            
            return result;
            
        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - startTime;
            System.out.println("⏱️  [TIMER] Failed: " + methodName + " after " + executionTime + "ms");
            throw throwable;
        }
    }
    
    /**
     * HELPER METHODS
     */
    
    private String getMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
    
    private String getArgumentsString(Object[] args) {
        if (args == null || args.length == 0) {
            return "none";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) sb.append(", ");
            
            Object arg = args[i];
            if (arg == null) {
                sb.append("null");
            } else if (arg instanceof String) {
                sb.append("\"").append(arg).append("\"");
            } else {
                sb.append(arg.toString());
            }
        }
        return sb.toString();
    }
    
    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    
    private void updateMethodStatistics(String methodName, long executionTime, boolean success) {
        methodStatistics.compute(methodName, (key, existingStats) -> {
            if (existingStats == null) {
                return new MethodStats(methodName, executionTime, success);
            } else {
                existingStats.addCall(executionTime, success);
                return existingStats;
            }
        });
    }
    
    private void printPerformanceResults(
            String methodName, 
            long executionTimeMs, 
            long executionTimeNano, 
            long memoryUsed,
            Object result, 
            Throwable exception) {
        
        System.out.println("   ⏱️  Execution Time: " + executionTimeMs + "ms (" + 
                          String.format("%.2f", executionTimeNano / 1_000_000.0) + "ms precise)");
        
        if (memoryUsed > 0) {
            System.out.println("   💾 Memory Used: +" + formatMemory(memoryUsed));
        } else if (memoryUsed < 0) {
            System.out.println("   💾 Memory Freed: " + formatMemory(-memoryUsed));
        } else {
            System.out.println("   💾 Memory: No significant change");
        }
        
        if (exception != null) {
            System.out.println("   ❌ Status: FAILED - " + exception.getClass().getSimpleName());
        } else {
            System.out.println("   ✅ Status: SUCCESS");
            if (result != null) {
                String resultStr = result.toString();
                if (resultStr.length() > 50) {
                    resultStr = resultStr.substring(0, 47) + "...";
                }
                System.out.println("   🎯 Result: " + resultStr);
            }
        }
        
        // Show current method statistics
        MethodStats stats = methodStatistics.get(methodName);
        if (stats != null) {
            System.out.println("   📈 Stats: " + stats.getCallCount() + " calls, " +
                              "avg " + stats.getAverageTime() + "ms, " +
                              String.format("%.1f", stats.getSuccessRate()) + "% success");
        }
        
        System.out.println("   ════════════════════════════════════════════");
    }
    
    private void checkPerformanceIssues(String methodName, long executionTime) {
        if (executionTime >= VERY_SLOW_METHOD_THRESHOLD_MS) {
            System.out.println("\n🚨 [ALERT] VERY SLOW METHOD DETECTED!");
            System.out.println("   Method: " + methodName);
            System.out.println("   Execution Time: " + executionTime + "ms");
            System.out.println("   Threshold: " + VERY_SLOW_METHOD_THRESHOLD_MS + "ms");
            System.out.println("   ⚠️  Consider optimization or async processing");
            
        } else if (executionTime >= SLOW_METHOD_THRESHOLD_MS) {
            System.out.println("\n⚠️  [WARNING] Slow method: " + methodName + 
                              " (" + executionTime + "ms)");
        }
    }
    
    private String formatMemory(long bytes) {
        if (bytes < 1024) return bytes + " bytes";
        if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    }
    
    /**
     * PUBLIC METHODS TO ACCESS PERFORMANCE DATA
     * 
     * These methods allow other parts of the application to access
     * the collected performance statistics.
     */
    
    public void printPerformanceSummary() {
        System.out.println("\n📊 PERFORMANCE SUMMARY");
        System.out.println("=".repeat(50));
        System.out.println("Total method calls: " + totalMethodCalls.get());
        System.out.println("Monitored methods: " + methodStatistics.size());
        System.out.println();
        
        // Sort methods by average execution time (slowest first)
        methodStatistics.values().stream()
            .sorted((a, b) -> Long.compare(b.getAverageTime(), a.getAverageTime()))
            .forEach(stats -> {
                System.out.printf("%-40s | %6d calls | %8.2fms avg | %5.1f%% success%n",
                    stats.getMethodName(),
                    stats.getCallCount(),
                    stats.getAverageTime(),
                    stats.getSuccessRate());
            });
        
        System.out.println("=".repeat(50));
    }
    
    public MethodStats getMethodStatistics(String methodName) {
        return methodStatistics.get(methodName);
    }
    
    public long getTotalMethodCalls() {
        return totalMethodCalls.get();
    }
    
    public void resetStatistics() {
        methodStatistics.clear();
        totalMethodCalls.set(0);
        System.out.println("📊 Performance statistics reset");
    }
}

/**
 * STATISTICS DATA CLASS
 * 
 * Thread-safe class to store performance statistics for each method
 */
class MethodStats {
    private final String methodName;
    private final AtomicLong callCount = new AtomicLong(0);
    private final AtomicLong totalTime = new AtomicLong(0);
    private final AtomicLong successCount = new AtomicLong(0);
    private volatile long minTime = Long.MAX_VALUE;
    private volatile long maxTime = Long.MIN_VALUE;
    
    public MethodStats(String methodName, long initialTime, boolean success) {
        this.methodName = methodName;
        addCall(initialTime, success);
    }
    
    public synchronized void addCall(long executionTime, boolean success) {
        callCount.incrementAndGet();
        totalTime.addAndGet(executionTime);
        
        if (success) {
            successCount.incrementAndGet();
        }
        
        if (executionTime < minTime) {
            minTime = executionTime;
        }
        if (executionTime > maxTime) {
            maxTime = executionTime;
        }
    }
    
    public String getMethodName() { return methodName; }
    public long getCallCount() { return callCount.get(); }
    public long getTotalTime() { return totalTime.get(); }
    public long getAverageTime() { 
        long calls = callCount.get();
        return calls == 0 ? 0 : totalTime.get() / calls; 
    }
    public long getMinTime() { return minTime == Long.MAX_VALUE ? 0 : minTime; }
    public long getMaxTime() { return maxTime == Long.MIN_VALUE ? 0 : maxTime; }
    public double getSuccessRate() { 
        long calls = callCount.get();
        return calls == 0 ? 100.0 : (successCount.get() * 100.0) / calls; 
    }
}

/**
 * USAGE EXAMPLES AND BEST PRACTICES:
 * 
 * 1. BASIC USAGE:
 *    Just add this aspect to your Spring configuration and it will
 *    automatically monitor all service and repository methods.
 * 
 * 2. CUSTOM MONITORING:
 *    Create a @Monitored annotation and apply it to specific methods
 *    that you want to monitor with simple timing.
 * 
 * 3. ACCESSING STATISTICS:
 *    Inject the PerformanceMonitoringAspect bean and call:
 *    - printPerformanceSummary() for a complete report
 *    - getMethodStatistics(methodName) for specific method stats
 * 
 * 4. PERFORMANCE TUNING:
 *    - Adjust SLOW_METHOD_THRESHOLD_MS based on your requirements
 *    - Modify pointcut expressions to monitor specific packages
 *    - Add more sophisticated alerting mechanisms
 * 
 * 5. PRODUCTION CONSIDERATIONS:
 *    - Consider using a proper metrics library (Micrometer, Dropwizard)
 *    - Store metrics in a time-series database (InfluxDB, Prometheus)
 *    - Add configuration to enable/disable monitoring
 *    - Implement sampling for high-throughput applications
 * 
 * 6. INTEGRATION WITH MONITORING TOOLS:
 *    - Export metrics to application performance monitoring (APM) tools
 *    - Create dashboards for real-time performance visualization
 *    - Set up alerts for performance degradation
 * 
 * This aspect demonstrates how AOP can provide enterprise-level
 * monitoring capabilities with minimal impact on business code!
 */ 