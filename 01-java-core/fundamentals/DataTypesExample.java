package com.learning.javacore.fundamentals;

/**
 * DataTypesExample.java
 * 
 * This class demonstrates various Java data types including:
 * 1. Primitive data types (8 types)
 * 2. Reference data types (objects, arrays, strings)
 * 3. Type conversion and casting
 * 4. Wrapper classes and autoboxing/unboxing
 * 
 * Learning Objectives:
 * - Understand the difference between primitive and reference types
 * - Learn about memory allocation for different types
 * - Master type conversion and casting techniques
 * - Understand autoboxing and unboxing concepts
 * 
 * @author Learning Journey
 * @version 1.0
 */
public class DataTypesExample {
    
    public static void main(String[] args) {
        System.out.println("=== Java Data Types Demonstration ===\n");
        
        // Call all demonstration methods
        demonstratePrimitiveTypes();
        demonstrateReferenceTypes();
        demonstrateTypeConversion();
        demonstrateWrapperClasses();
        demonstrateAutoboxingUnboxing();
    }
    
    /**
     * Demonstrates all 8 primitive data types in Java
     * Primitive types are stored directly in memory (stack)
     * They have default values when declared as instance variables
     */
    private static void demonstratePrimitiveTypes() {
        System.out.println("1. PRIMITIVE DATA TYPES");
        System.out.println("========================");
        
        // Integer types - whole numbers
        byte age = 25;              // 8-bit signed integer (-128 to 127)
        short year = 2024;          // 16-bit signed integer (-32,768 to 32,767)
        int population = 1000000;   // 32-bit signed integer (-2^31 to 2^31-1)
        long distance = 9460730472580800L; // 64-bit signed integer (note the 'L' suffix)
        
        // Floating-point types - decimal numbers
        float price = 19.99f;       // 32-bit IEEE 754 floating point (note the 'f' suffix)
        double pi = 3.14159265359;  // 64-bit IEEE 754 floating point
        
        // Character type - single Unicode character
        char grade = 'A';           // 16-bit Unicode character
        char unicode = '\u0041';    // Unicode representation of 'A'
        
        // Boolean type - true or false
        boolean isActive = true;    // Only two possible values: true or false
        
        // Display all primitive types
        System.out.println("byte age = " + age + " (size: 8 bits)");
        System.out.println("short year = " + year + " (size: 16 bits)");
        System.out.println("int population = " + population + " (size: 32 bits)");
        System.out.println("long distance = " + distance + " (size: 64 bits)");
        System.out.println("float price = " + price + " (size: 32 bits)");
        System.out.println("double pi = " + pi + " (size: 64 bits)");
        System.out.println("char grade = " + grade + " (size: 16 bits)");
        System.out.println("char unicode = " + unicode + " (Unicode: \\u0041)");
        System.out.println("boolean isActive = " + isActive + " (size: 1 bit, but JVM dependent)");
        
        // Demonstrate min/max values
        System.out.println("\nMin/Max Values:");
        System.out.println("byte: " + Byte.MIN_VALUE + " to " + Byte.MAX_VALUE);
        System.out.println("int: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
        System.out.println("long: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
        
        System.out.println();
    }
    
    /**
     * Demonstrates reference data types
     * Reference types store addresses to objects in heap memory
     * They can be null and have methods associated with them
     */
    private static void demonstrateReferenceTypes() {
        System.out.println("2. REFERENCE DATA TYPES");
        System.out.println("========================");
        
        // String - most commonly used reference type
        String message = "Hello, Java!";  // String literal (stored in string pool)
        String name = new String("John"); // String object (stored in heap)
        
        // Arrays - collection of elements of same type
        int[] numbers = {1, 2, 3, 4, 5};          // Array literal
        String[] names = new String[3];            // Array with specified size
        names[0] = "Alice";
        names[1] = "Bob";
        names[2] = "Charlie";
        
        // Multi-dimensional array
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        // Display reference types
        System.out.println("String message = \"" + message + "\"");
        System.out.println("String name = \"" + name + "\"");
        System.out.println("String comparison: message == name is " + (message == name));
        System.out.println("String comparison: message.equals(name) is " + message.equals(name));
        
        System.out.println("\nArray examples:");
        System.out.print("int[] numbers = ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println("\nArray length: " + numbers.length);
        
        System.out.println("String[] names:");
        for (String n : names) {
            System.out.println("  " + n);
        }
        
        System.out.println("2D Array matrix:");
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates type conversion and casting
     * Implicit conversion (widening) vs Explicit conversion (narrowing)
     */
    private static void demonstrateTypeConversion() {
        System.out.println("3. TYPE CONVERSION AND CASTING");
        System.out.println("===============================");
        
        // Implicit conversion (widening) - automatic, no data loss
        int intValue = 100;
        long longValue = intValue;        // int to long (automatic)
        float floatValue = intValue;      // int to float (automatic)
        double doubleValue = floatValue;  // float to double (automatic)
        
        System.out.println("Implicit Conversion (Widening):");
        System.out.println("int " + intValue + " -> long " + longValue);
        System.out.println("int " + intValue + " -> float " + floatValue);
        System.out.println("float " + floatValue + " -> double " + doubleValue);
        
        // Explicit conversion (narrowing) - manual, potential data loss
        double originalDouble = 123.45;
        int convertedInt = (int) originalDouble;     // Truncates decimal part
        byte convertedByte = (byte) intValue;        // May lose data if out of range
        
        System.out.println("\nExplicit Conversion (Narrowing):");
        System.out.println("double " + originalDouble + " -> int " + convertedInt);
        System.out.println("int " + intValue + " -> byte " + convertedByte);
        
        // String to numeric conversion
        String numberString = "456";
        int parsedInt = Integer.parseInt(numberString);
        double parsedDouble = Double.parseDouble("789.12");
        
        System.out.println("\nString to Numeric Conversion:");
        System.out.println("String \"" + numberString + "\" -> int " + parsedInt);
        System.out.println("String \"789.12\" -> double " + parsedDouble);
        
        // Numeric to String conversion
        int number = 42;
        String numberToString = String.valueOf(number);
        String anotherWay = Integer.toString(number);
        
        System.out.println("\nNumeric to String Conversion:");
        System.out.println("int " + number + " -> String \"" + numberToString + "\"");
        System.out.println("int " + number + " -> String \"" + anotherWay + "\" (using toString)");
        
        System.out.println();
    }
    
    /**
     * Demonstrates wrapper classes for primitive types
     * Each primitive type has a corresponding wrapper class
     */
    private static void demonstrateWrapperClasses() {
        System.out.println("4. WRAPPER CLASSES");
        System.out.println("===================");
        
        // Wrapper classes for each primitive type
        Byte byteWrapper = Byte.valueOf((byte) 10);
        Short shortWrapper = Short.valueOf((short) 100);
        Integer intWrapper = Integer.valueOf(1000);
        Long longWrapper = Long.valueOf(10000L);
        Float floatWrapper = Float.valueOf(3.14f);
        Double doubleWrapper = Double.valueOf(2.71828);
        Character charWrapper = Character.valueOf('X');
        Boolean boolWrapper = Boolean.valueOf(true);
        
        System.out.println("Wrapper Objects:");
        System.out.println("Byte: " + byteWrapper);
        System.out.println("Short: " + shortWrapper);
        System.out.println("Integer: " + intWrapper);
        System.out.println("Long: " + longWrapper);
        System.out.println("Float: " + floatWrapper);
        System.out.println("Double: " + doubleWrapper);
        System.out.println("Character: " + charWrapper);
        System.out.println("Boolean: " + boolWrapper);
        
        // Useful wrapper class methods
        System.out.println("\nUseful Wrapper Class Methods:");
        System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);
        System.out.println("Double.isNaN(0.0/0.0): " + Double.isNaN(0.0/0.0));
        System.out.println("Character.isDigit('5'): " + Character.isDigit('5'));
        System.out.println("Character.toUpperCase('a'): " + Character.toUpperCase('a'));
        System.out.println("Boolean.parseBoolean(\"true\"): " + Boolean.parseBoolean("true"));
        
        System.out.println();
    }
    
    /**
     * Demonstrates autoboxing and unboxing
     * Autoboxing: automatic conversion from primitive to wrapper
     * Unboxing: automatic conversion from wrapper to primitive
     */
    private static void demonstrateAutoboxingUnboxing() {
        System.out.println("5. AUTOBOXING AND UNBOXING");
        System.out.println("===========================");
        
        // Autoboxing - primitive to wrapper (automatic)
        Integer autoboxedInt = 42;        // int -> Integer (autoboxing)
        Double autoboxedDouble = 3.14;    // double -> Double (autoboxing)
        Boolean autoboxedBool = true;     // boolean -> Boolean (autoboxing)
        
        System.out.println("Autoboxing Examples:");
        System.out.println("int 42 -> Integer: " + autoboxedInt);
        System.out.println("double 3.14 -> Double: " + autoboxedDouble);
        System.out.println("boolean true -> Boolean: " + autoboxedBool);
        
        // Unboxing - wrapper to primitive (automatic)
        Integer wrapperInt = Integer.valueOf(100);
        int unboxedInt = wrapperInt;      // Integer -> int (unboxing)
        
        Double wrapperDouble = Double.valueOf(2.71);
        double unboxedDouble = wrapperDouble; // Double -> double (unboxing)
        
        System.out.println("\nUnboxing Examples:");
        System.out.println("Integer " + wrapperInt + " -> int: " + unboxedInt);
        System.out.println("Double " + wrapperDouble + " -> double: " + unboxedDouble);
        
        // Collections work with wrapper classes only
        java.util.List<Integer> numbers = java.util.Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("\nCollections use wrapper classes:");
        System.out.println("List<Integer>: " + numbers);
        
        // Arithmetic operations with autoboxing/unboxing
        Integer a = 10;
        Integer b = 20;
        Integer sum = a + b;  // Unbox a and b, add, then autobox result
        
        System.out.println("\nArithmetic with autoboxing/unboxing:");
        System.out.println("Integer " + a + " + Integer " + b + " = Integer " + sum);
        
        // Performance consideration
        System.out.println("\nPerformance Note:");
        System.out.println("Excessive autoboxing/unboxing can impact performance.");
        System.out.println("Use primitives for calculations, wrappers for collections.");
        
        System.out.println();
    }
} 