package com.learning.javacore.fundamentals;

import java.util.Scanner;

/**
 * ControlStructuresExample.java
 * 
 * This class demonstrates various Java control structures:
 * 1. Decision-making structures (if, if-else, switch)
 * 2. Looping structures (for, while, do-while, enhanced for)
 * 3. Jump statements (break, continue, return)
 * 4. Nested control structures
 * 
 * Learning Objectives:
 * - Master conditional logic implementation
 * - Understand different types of loops and when to use them
 * - Learn flow control with jump statements
 * - Practice nested structures and complex logic
 * 
 * @author Learning Journey
 * @version 1.0
 */
public class ControlStructuresExample {
    
    public static void main(String[] args) {
        System.out.println("=== Java Control Structures Demonstration ===\n");
        
        demonstrateIfStatements();
        demonstrateSwitchStatements();
        demonstrateLoops();
        demonstrateJumpStatements();
        demonstrateNestedStructures();
        practicalExample();
    }
    
    /**
     * Demonstrates if, if-else, and if-else-if statements
     * These are used for decision making based on conditions
     */
    private static void demonstrateIfStatements() {
        System.out.println("1. IF STATEMENTS");
        System.out.println("================");
        
        int age = 20;
        double grade = 85.5;
        boolean isStudent = true;
        
        // Simple if statement
        if (age >= 18) {
            System.out.println("You are an adult (age: " + age + ")");
        }
        
        // If-else statement
        if (grade >= 90) {
            System.out.println("Excellent grade!");
        } else {
            System.out.println("Good grade, keep improving! (grade: " + grade + ")");
        }
        
        // If-else-if ladder
        if (grade >= 90) {
            System.out.println("Grade: A");
        } else if (grade >= 80) {
            System.out.println("Grade: B");
        } else if (grade >= 70) {
            System.out.println("Grade: C");
        } else if (grade >= 60) {
            System.out.println("Grade: D");
        } else {
            System.out.println("Grade: F");
        }
        
        // Complex conditions with logical operators
        if (age >= 18 && isStudent) {
            System.out.println("Adult student - eligible for student discount");
        }
        
        if (age < 13 || age > 65) {
            System.out.println("Special pricing category");
        }
        
        // Ternary operator (shorthand if-else)
        String status = (age >= 18) ? "Adult" : "Minor";
        System.out.println("Status using ternary operator: " + status);
        
        System.out.println();
    }
    
    /**
     * Demonstrates switch statements for multi-way branching
     * More efficient than if-else-if when comparing single variable against multiple values
     */
    private static void demonstrateSwitchStatements() {
        System.out.println("2. SWITCH STATEMENTS");
        System.out.println("====================");
        
        // Traditional switch with integers
        int dayNumber = 3;
        String dayName;
        
        switch (dayNumber) {
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            case 6:
                dayName = "Saturday";
                break;
            case 7:
                dayName = "Sunday";
                break;
            default:
                dayName = "Invalid day";
                break;
        }
        System.out.println("Day " + dayNumber + " is " + dayName);
        
        // Switch with strings (Java 7+)
        String month = "March";
        int days;
        
        switch (month.toLowerCase()) {
            case "january":
            case "march":
            case "may":
            case "july":
            case "august":
            case "october":
            case "december":
                days = 31;
                break;
            case "april":
            case "june":
            case "september":
            case "november":
                days = 30;
                break;
            case "february":
                days = 28; // Not considering leap year for simplicity
                break;
            default:
                days = 0;
                System.out.println("Invalid month");
        }
        
        if (days > 0) {
            System.out.println(month + " has " + days + " days");
        }
        
        // Switch expressions (Java 14+) - more concise
        char grade = 'B';
        String performance = switch (grade) {
            case 'A' -> "Excellent";
            case 'B' -> "Good";
            case 'C' -> "Average";
            case 'D' -> "Below Average";
            case 'F' -> "Fail";
            default -> "Invalid grade";
        };
        System.out.println("Grade " + grade + " performance: " + performance);
        
        System.out.println();
    }
    
    /**
     * Demonstrates different types of loops
     * Loops are used for repetitive execution of code blocks
     */
    private static void demonstrateLoops() {
        System.out.println("3. LOOPS");
        System.out.println("========");
        
        // For loop - used when number of iterations is known
        System.out.println("For loop - counting 1 to 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // For loop with different increments
        System.out.println("For loop - even numbers from 2 to 10:");
        for (int i = 2; i <= 10; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // While loop - used when condition is checked before execution
        System.out.println("While loop - countdown from 5:");
        int countdown = 5;
        while (countdown > 0) {
            System.out.print(countdown + " ");
            countdown--;
        }
        System.out.println("Blast off!");
        
        // Do-while loop - executes at least once, condition checked after execution
        System.out.println("Do-while loop - user input simulation:");
        int attempts = 0;
        int maxAttempts = 3;
        do {
            attempts++;
            System.out.println("Attempt " + attempts + " - trying to connect...");
        } while (attempts < maxAttempts);
        System.out.println("Connection attempts completed");
        
        // Enhanced for loop (for-each) - used for iterating over arrays/collections
        System.out.println("Enhanced for loop - array iteration:");
        String[] fruits = {"Apple", "Banana", "Orange", "Mango"};
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }
        
        // Nested loops - loops inside loops
        System.out.println("Nested loops - multiplication table:");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print(i * j + "\t");
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates jump statements that alter the flow of execution
     * break, continue, and return statements
     */
    private static void demonstrateJumpStatements() {
        System.out.println("4. JUMP STATEMENTS");
        System.out.println("==================");
        
        // Break statement - exits from loop or switch
        System.out.println("Break statement - exit loop when number is found:");
        int[] numbers = {10, 20, 30, 40, 50};
        int target = 30;
        
        for (int number : numbers) {
            if (number == target) {
                System.out.println("Found target: " + target);
                break; // Exit the loop
            }
            System.out.println("Checking: " + number);
        }
        
        // Continue statement - skips current iteration
        System.out.println("\nContinue statement - skip even numbers:");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // Skip even numbers
            }
            System.out.print(i + " ");
        }
        System.out.println();
        
        // Labeled break and continue - for nested loops
        System.out.println("\nLabeled break - exit nested loops:");
        outer: for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("Breaking out of both loops at i=" + i + ", j=" + j);
                    break outer; // Break out of both loops
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates nested control structures
     * Combining multiple control structures for complex logic
     */
    private static void demonstrateNestedStructures() {
        System.out.println("5. NESTED CONTROL STRUCTURES");
        System.out.println("=============================");
        
        // Nested if statements within loops
        System.out.println("Number categorization:");
        for (int i = 1; i <= 15; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println(i + ": FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println(i + ": Fizz");
            } else if (i % 5 == 0) {
                System.out.println(i + ": Buzz");
            } else {
                System.out.println(i + ": Number");
            }
        }
        
        // Switch inside loop
        System.out.println("\nProcessing different data types:");
        String[] dataTypes = {"int", "string", "double", "boolean", "char"};
        
        for (String dataType : dataTypes) {
            switch (dataType) {
                case "int":
                    System.out.println("Processing integer data...");
                    for (int i = 1; i <= 3; i++) {
                        System.out.println("  Integer " + i + ": " + (i * 10));
                    }
                    break;
                case "string":
                    System.out.println("Processing string data...");
                    String[] words = {"Hello", "World", "Java"};
                    for (String word : words) {
                        System.out.println("  String: " + word.toUpperCase());
                    }
                    break;
                case "double":
                    System.out.println("Processing double data...");
                    for (double d = 1.1; d <= 3.1; d += 1.0) {
                        System.out.println("  Double: " + String.format("%.1f", d));
                    }
                    break;
                default:
                    System.out.println("Processing " + dataType + " data...");
            }
        }
        
        System.out.println();
    }
    
    /**
     * Practical example combining all control structures
     * A simple number guessing game simulation
     */
    private static void practicalExample() {
        System.out.println("6. PRACTICAL EXAMPLE - NUMBER ANALYSIS");
        System.out.println("=======================================");
        
        int[] testNumbers = {7, 12, 25, 33, 48, 51, 67, 84, 99};
        
        System.out.println("Analyzing numbers: ");
        for (int num : testNumbers) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // Analyze each number
        for (int number : testNumbers) {
            System.out.print("Number " + number + ": ");
            
            // Check if prime
            boolean isPrime = true;
            if (number <= 1) {
                isPrime = false;
            } else {
                for (int i = 2; i <= Math.sqrt(number); i++) {
                    if (number % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }
            
            // Categorize the number
            if (isPrime) {
                System.out.print("Prime ");
            }
            
            if (number % 2 == 0) {
                System.out.print("Even ");
            } else {
                System.out.print("Odd ");
            }
            
            // Range categorization
            if (number < 25) {
                System.out.print("Small");
            } else if (number <= 75) {
                System.out.print("Medium");
            } else {
                System.out.print("Large");
            }
            
            System.out.println();
        }
        
        System.out.println("\nAnalysis completed!");
        System.out.println();
    }
} 