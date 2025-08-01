package com.learning.aop.basic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AopConfig.java
 * 
 * This is the configuration class that enables AOP (Aspect-Oriented Programming) in Spring.
 * 
 * Key Annotations:
 * - @Configuration: Marks this as a Spring configuration class
 * - @EnableAspectJAutoProxy: Enables AspectJ-style aspects in Spring
 * - @ComponentScan: Tells Spring where to look for components, services, and aspects
 * 
 * What happens when you enable AOP:
 * 1. Spring creates proxy objects for beans that have aspects applied
 * 2. When methods are called, Spring intercepts these calls
 * 3. Aspects are executed at the appropriate times (before, after, around, etc.)
 * 4. The original method is then executed
 * 
 * Types of Proxies Spring Uses:
 * - JDK Dynamic Proxy: For classes that implement interfaces
 * - CGLIB Proxy: For classes that don't implement interfaces
 * 
 * @author Learning Journey
 * @version 1.0
 */
@Configuration
@EnableAspectJAutoProxy  // This annotation is the key to enabling AOP in Spring
@ComponentScan(basePackages = "com.learning.aop.basic")  // Scan for components in this package
public class AopConfig {
    
    /**
     * Configuration for AOP behavior
     * 
     * @EnableAspectJAutoProxy parameters:
     * - proxyTargetClass = true: Forces CGLIB proxies (class-based)
     * - proxyTargetClass = false: Uses JDK dynamic proxies when possible (interface-based)
     * - exposeProxy = true: Allows access to current proxy via AopContext.currentProxy()
     */
    
    /*
     * HOW SPRING AOP WORKS BEHIND THE SCENES:
     * 
     * 1. BEAN CREATION PHASE:
     *    - Spring scans for @Aspect annotated classes
     *    - Spring analyzes pointcut expressions
     *    - Spring identifies which beans need to be proxied
     * 
     * 2. PROXY CREATION PHASE:
     *    - For each target bean, Spring creates a proxy
     *    - The proxy wraps the original object
     *    - Aspects are woven into the proxy
     * 
     * 3. METHOD INVOCATION PHASE:
     *    - Client calls method on proxy (not original object)
     *    - Proxy executes applicable advice (before, around, etc.)
     *    - Proxy delegates to original object method
     *    - Proxy executes more advice (after, afterReturning, etc.)
     * 
     * EXAMPLE FLOW:
     * Client → Proxy → @Before Advice → Original Method → @After Advice → Response
     */
    
    /**
     * This method demonstrates how you can programmatically configure AOP
     * if you need more control than annotations provide
     */
    // @Bean
    // public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    //     DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    //     creator.setProxyTargetClass(true); // Use CGLIB proxies
    //     return creator;
    // }
    
    /**
     * COMMON AOP CONFIGURATION GOTCHAS:
     * 
     * 1. SELF-INVOCATION PROBLEM:
     *    - AOP doesn't work for method calls within the same class
     *    - Solution: Inject the bean into itself or use @Async
     * 
     * 2. FINAL METHODS:
     *    - AOP cannot proxy final methods with CGLIB
     *    - Solution: Use interfaces or avoid final methods
     * 
     * 3. PRIVATE METHODS:
     *    - AOP cannot proxy private methods
     *    - Solution: Make methods public/protected or use AspectJ weaving
     * 
     * 4. PERFORMANCE CONSIDERATIONS:
     *    - Proxies add overhead to method calls
     *    - Use AOP judiciously for cross-cutting concerns only
     */
}

/**
 * ALTERNATIVE CONFIGURATION APPROACHES:
 * 
 * 1. XML CONFIGURATION (Legacy):
 * <aop:aspectj-autoproxy/>
 * <context:component-scan base-package="com.learning.aop"/>
 * 
 * 2. JAVA CONFIG WITH EXPLICIT ASPECT BEANS:
 * @Bean
 * public LoggingAspect loggingAspect() {
 *     return new LoggingAspect();
 * }
 * 
 * 3. SPRING BOOT AUTO-CONFIGURATION:
 * In Spring Boot, AOP is auto-configured when spring-boot-starter-aop is included
 * 
 * DEBUGGING TIPS:
 * 
 * 1. Check if proxy is created:
 *    System.out.println(AopUtils.isAopProxy(yourBean));
 * 
 * 2. Check proxy type:
 *    System.out.println(AopUtils.isCglibProxy(yourBean));
 *    System.out.println(AopUtils.isJdkDynamicProxy(yourBean));
 * 
 * 3. Enable AOP logging:
 *    logging.level.org.springframework.aop=DEBUG
 */ 