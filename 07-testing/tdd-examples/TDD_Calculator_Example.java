package com.learning.testing.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * TDD_Calculator_Example.java
 * 
 * This example demonstrates Test-Driven Development (TDD) using the Red-Green-Refactor cycle:
 * 
 * TDD Process:
 * 1. RED: Write a failing test (defines what we want)
 * 2. GREEN: Write minimal code to make the test pass (make it work)
 * 3. REFACTOR: Improve the code while keeping tests green (make it better)
 * 
 * Benefits of TDD:
 * - Better design through thinking about usage first
 * - Higher test coverage (100% by definition)
 * - Immediate feedback on requirements
 * - Regression protection
 * - Documentation through tests
 * 
 * This example builds a Calculator class step by step using TDD principles.
 * 
 * @author Learning Journey
 * @version 1.0
 */
public class TDD_Calculator_Example {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    // ========================================================================
    // STEP 1: ADDITION - Red-Green-Refactor Cycle
    // ========================================================================
    
    @Test
    @DisplayName("Should add two positive numbers correctly")
    void shouldAddTwoPositiveNumbers() {
        // RED PHASE: Write failing test first
        // This test will fail because Calculator class doesn't exist yet
        
        // Arrange
        double a = 5.0;
        double b = 3.0;
        double expected = 8.0;
        
        // Act
        double result = calculator.add(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "5.0 + 3.0 should equal 8.0");
    }
    
    @Test
    @DisplayName("Should add negative numbers correctly")
    void shouldAddNegativeNumbers() {
        // Arrange
        double a = -5.0;
        double b = -3.0;
        double expected = -8.0;
        
        // Act
        double result = calculator.add(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "-5.0 + (-3.0) should equal -8.0");
    }
    
    @Test
    @DisplayName("Should add zero correctly")
    void shouldAddZero() {
        // Arrange
        double a = 5.0;
        double b = 0.0;
        double expected = 5.0;
        
        // Act
        double result = calculator.add(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "5.0 + 0.0 should equal 5.0");
    }
    
    // ========================================================================
    // STEP 2: SUBTRACTION - Expanding functionality
    // ========================================================================
    
    @Test
    @DisplayName("Should subtract two numbers correctly")
    void shouldSubtractTwoNumbers() {
        // Arrange
        double a = 10.0;
        double b = 3.0;
        double expected = 7.0;
        
        // Act
        double result = calculator.subtract(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "10.0 - 3.0 should equal 7.0");
    }
    
    @Test
    @DisplayName("Should handle negative subtraction results")
    void shouldHandleNegativeSubtractionResults() {
        // Arrange
        double a = 3.0;
        double b = 10.0;
        double expected = -7.0;
        
        // Act
        double result = calculator.subtract(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "3.0 - 10.0 should equal -7.0");
    }
    
    // ========================================================================
    // STEP 3: MULTIPLICATION - More complex operations
    // ========================================================================
    
    @Test
    @DisplayName("Should multiply two positive numbers")
    void shouldMultiplyTwoPositiveNumbers() {
        // Arrange
        double a = 4.0;
        double b = 3.0;
        double expected = 12.0;
        
        // Act
        double result = calculator.multiply(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "4.0 * 3.0 should equal 12.0");
    }
    
    @Test
    @DisplayName("Should handle multiplication by zero")
    void shouldHandleMultiplicationByZero() {
        // Arrange
        double a = 5.0;
        double b = 0.0;
        double expected = 0.0;
        
        // Act
        double result = calculator.multiply(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "5.0 * 0.0 should equal 0.0");
    }
    
    @Test
    @DisplayName("Should handle negative multiplication")
    void shouldHandleNegativeMultiplication() {
        // Arrange
        double a = -4.0;
        double b = 3.0;
        double expected = -12.0;
        
        // Act
        double result = calculator.multiply(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "-4.0 * 3.0 should equal -12.0");
    }
    
    // ========================================================================
    // STEP 4: DIVISION - Error handling and edge cases
    // ========================================================================
    
    @Test
    @DisplayName("Should divide two numbers correctly")
    void shouldDivideTwoNumbers() {
        // Arrange
        double a = 15.0;
        double b = 3.0;
        double expected = 5.0;
        
        // Act
        double result = calculator.divide(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "15.0 / 3.0 should equal 5.0");
    }
    
    @Test
    @DisplayName("Should handle division resulting in decimal")
    void shouldHandleDivisionResultingInDecimal() {
        // Arrange
        double a = 10.0;
        double b = 3.0;
        double expected = 3.333333;
        
        // Act
        double result = calculator.divide(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, "10.0 / 3.0 should equal approximately 3.333");
    }
    
    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void shouldThrowExceptionWhenDividingByZero() {
        // Arrange
        double a = 10.0;
        double b = 0.0;
        
        // Act & Assert
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(a, b),
            "Division by zero should throw ArithmeticException"
        );
        
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }
    
    // ========================================================================
    // STEP 5: ADVANCED OPERATIONS - Power and Square Root
    // ========================================================================
    
    @Test
    @DisplayName("Should calculate power correctly")
    void shouldCalculatePowerCorrectly() {
        // Arrange
        double base = 2.0;
        double exponent = 3.0;
        double expected = 8.0;
        
        // Act
        double result = calculator.power(base, exponent);
        
        // Assert
        assertEquals(expected, result, 0.001, "2.0^3.0 should equal 8.0");
    }
    
    @Test
    @DisplayName("Should handle power of zero")
    void shouldHandlePowerOfZero() {
        // Arrange
        double base = 5.0;
        double exponent = 0.0;
        double expected = 1.0;
        
        // Act
        double result = calculator.power(base, exponent);
        
        // Assert
        assertEquals(expected, result, 0.001, "Any number to power 0 should equal 1.0");
    }
    
    @Test
    @DisplayName("Should calculate square root correctly")
    void shouldCalculateSquareRootCorrectly() {
        // Arrange
        double number = 16.0;
        double expected = 4.0;
        
        // Act
        double result = calculator.squareRoot(number);
        
        // Assert
        assertEquals(expected, result, 0.001, "Square root of 16.0 should equal 4.0");
    }
    
    @Test
    @DisplayName("Should throw exception for negative square root")
    void shouldThrowExceptionForNegativeSquareRoot() {
        // Arrange
        double number = -16.0;
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.squareRoot(number),
            "Square root of negative number should throw IllegalArgumentException"
        );
        
        assertEquals("Cannot calculate square root of negative number", exception.getMessage());
    }
    
    // ========================================================================
    // STEP 6: PARAMETERIZED TESTS - Testing multiple inputs efficiently
    // ========================================================================
    
    @ParameterizedTest
    @DisplayName("Should add various number combinations correctly")
    @CsvSource({
        "1.0, 2.0, 3.0",
        "0.0, 0.0, 0.0",
        "-1.0, 1.0, 0.0",
        "100.0, 200.0, 300.0",
        "-50.0, -30.0, -80.0"
    })
    void shouldAddVariousNumberCombinations(double a, double b, double expected) {
        // Act
        double result = calculator.add(a, b);
        
        // Assert
        assertEquals(expected, result, 0.001, 
            String.format("%.1f + %.1f should equal %.1f", a, b, expected));
    }
    
    @ParameterizedTest
    @DisplayName("Should multiply by various factors correctly")
    @ValueSource(doubles = {0.0, 1.0, 2.0, -1.0, 0.5, 10.0})
    void shouldMultiplyByVariousFactors(double factor) {
        // Arrange
        double base = 6.0;
        double expected = base * factor;
        
        // Act
        double result = calculator.multiply(base, factor);
        
        // Assert
        assertEquals(expected, result, 0.001,
            String.format("%.1f * %.1f should equal %.1f", base, factor, expected));
    }
    
    // ========================================================================
    // STEP 7: MEMORY OPERATIONS - Demonstrating stateful testing
    // ========================================================================
    
    @Test
    @DisplayName("Should store and retrieve memory value")
    void shouldStoreAndRetrieveMemoryValue() {
        // Arrange
        double value = 42.0;
        
        // Act
        calculator.memoryStore(value);
        double result = calculator.memoryRecall();
        
        // Assert
        assertEquals(value, result, 0.001, "Memory should store and recall the same value");
    }
    
    @Test
    @DisplayName("Should clear memory")
    void shouldClearMemory() {
        // Arrange
        calculator.memoryStore(100.0);
        
        // Act
        calculator.memoryClear();
        double result = calculator.memoryRecall();
        
        // Assert
        assertEquals(0.0, result, 0.001, "Memory should be cleared to 0.0");
    }
    
    @Test
    @DisplayName("Should add to memory")
    void shouldAddToMemory() {
        // Arrange
        calculator.memoryStore(10.0);
        double valueToAdd = 5.0;
        double expected = 15.0;
        
        // Act
        calculator.memoryAdd(valueToAdd);
        double result = calculator.memoryRecall();
        
        // Assert
        assertEquals(expected, result, 0.001, "Memory should add value to existing memory");
    }
}

/**
 * Calculator class - Implementation created through TDD process
 * 
 * This class was built incrementally following the TDD cycle:
 * 1. Write failing test
 * 2. Write minimal code to pass
 * 3. Refactor for better design
 * 
 * Each method was added only when a test required it.
 */
class Calculator {
    
    private double memory = 0.0;
    
    // ========================================================================
    // BASIC ARITHMETIC OPERATIONS
    // ========================================================================
    
    /**
     * Adds two numbers
     * Implementation: Simple addition operation
     */
    public double add(double a, double b) {
        return a + b;
    }
    
    /**
     * Subtracts second number from first
     * Implementation: Simple subtraction operation
     */
    public double subtract(double a, double b) {
        return a - b;
    }
    
    /**
     * Multiplies two numbers
     * Implementation: Simple multiplication operation
     */
    public double multiply(double a, double b) {
        return a * b;
    }
    
    /**
     * Divides first number by second
     * Implementation: Division with zero-check
     */
    public double divide(double a, double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }
    
    // ========================================================================
    // ADVANCED OPERATIONS
    // ========================================================================
    
    /**
     * Calculates base raised to the power of exponent
     * Implementation: Using Math.pow()
     */
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    
    /**
     * Calculates square root of a number
     * Implementation: Using Math.sqrt() with negative check
     */
    public double squareRoot(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        return Math.sqrt(number);
    }
    
    // ========================================================================
    // MEMORY OPERATIONS
    // ========================================================================
    
    /**
     * Stores value in memory
     * Implementation: Simple assignment to memory field
     */
    public void memoryStore(double value) {
        this.memory = value;
    }
    
    /**
     * Recalls value from memory
     * Implementation: Returns current memory value
     */
    public double memoryRecall() {
        return this.memory;
    }
    
    /**
     * Clears memory (sets to zero)
     * Implementation: Reset memory to 0.0
     */
    public void memoryClear() {
        this.memory = 0.0;
    }
    
    /**
     * Adds value to current memory
     * Implementation: Add parameter to existing memory value
     */
    public void memoryAdd(double value) {
        this.memory += value;
    }
}

/**
 * TDD LESSONS LEARNED:
 * 
 * 1. Start Simple: Begin with the simplest test cases
 * 2. One Test at a Time: Focus on making one test pass before writing the next
 * 3. Refactor Safely: Improve code structure while keeping all tests green
 * 4. Test Edge Cases: Include boundary conditions and error scenarios
 * 5. Clear Test Names: Use descriptive names that explain the expected behavior
 * 6. Fast Feedback: Keep tests fast to maintain the TDD rhythm
 * 7. Design Emerges: Good design emerges naturally from writing tests first
 * 
 * TDD BENEFITS DEMONSTRATED:
 * 
 * ✅ 100% Test Coverage: Every line of code is covered by tests
 * ✅ Regression Protection: Changes can't break existing functionality
 * ✅ Living Documentation: Tests document how the calculator should behave
 * ✅ Better Design: API designed from user's perspective (test's perspective)
 * ✅ Confidence: Safe to refactor and enhance functionality
 * ✅ Fast Feedback: Immediate notification when something breaks
 * 
 * NEXT STEPS:
 * 
 * - Add more complex operations (trigonometric functions)
 * - Implement calculation history
 * - Add support for complex numbers
 * - Create GUI interface (with tests for the controller layer)
 */ 