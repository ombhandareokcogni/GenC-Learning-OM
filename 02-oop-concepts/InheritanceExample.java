package com.learning.oop;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;
import java.util.ArrayList;

/**
 * Inheritance Example using Spring
 * Demonstrates inheritance hierarchy with Spring-managed beans
 */

// Base class - Parent class
@Component
abstract class Vehicle {
    protected String brand;
    protected String model;
    protected int year;
    protected double price;
    
    public Vehicle() {}
    
    public Vehicle(String brand, String model, int year, double price) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }
    
    // Common method for all vehicles
    public void start() {
        System.out.println(brand + " " + model + " is starting...");
    }
    
    public void stop() {
        System.out.println(brand + " " + model + " has stopped.");
    }
    
    // Abstract method to be implemented by child classes
    public abstract void displaySpecifications();
    
    // Common getters and setters
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    @Override
    public String toString() {
        return String.format("%s %s (%d) - $%.2f", brand, model, year, price);
    }
}

// Child class 1 - Inherits from Vehicle
@Component
class Car extends Vehicle {
    private int numberOfDoors;
    private String fuelType;
    
    public Car() {
        super();
    }
    
    public Car(String brand, String model, int year, double price, int numberOfDoors, String fuelType) {
        super(brand, model, year, price); // Call parent constructor
        this.numberOfDoors = numberOfDoors;
        this.fuelType = fuelType;
    }
    
    // Override parent method
    @Override
    public void start() {
        System.out.println("Car engine starting with key ignition...");
        super.start(); // Call parent method
    }
    
    // Implementation of abstract method
    @Override
    public void displaySpecifications() {
        System.out.println("=== Car Specifications ===");
        System.out.println("Vehicle: " + toString());
        System.out.println("Doors: " + numberOfDoors);
        System.out.println("Fuel Type: " + fuelType);
    }
    
    // Car-specific method
    public void honk() {
        System.out.println(brand + " " + model + " is honking: Beep! Beep!");
    }
    
    // Getters and setters for car-specific properties
    public int getNumberOfDoors() { return numberOfDoors; }
    public void setNumberOfDoors(int numberOfDoors) { this.numberOfDoors = numberOfDoors; }
    
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
}

// Child class 2 - Inherits from Vehicle
@Component
class Motorcycle extends Vehicle {
    private String engineType;
    private boolean hasSidecar;
    
    public Motorcycle() {
        super();
    }
    
    public Motorcycle(String brand, String model, int year, double price, String engineType, boolean hasSidecar) {
        super(brand, model, year, price);
        this.engineType = engineType;
        this.hasSidecar = hasSidecar;
    }
    
    // Override parent method with different behavior
    @Override
    public void start() {
        System.out.println("Motorcycle starting with kick start...");
        super.start();
    }
    
    // Implementation of abstract method
    @Override
    public void displaySpecifications() {
        System.out.println("=== Motorcycle Specifications ===");
        System.out.println("Vehicle: " + toString());
        System.out.println("Engine Type: " + engineType);
        System.out.println("Has Sidecar: " + (hasSidecar ? "Yes" : "No"));
    }
    
    // Motorcycle-specific method
    public void wheelie() {
        System.out.println(brand + " " + model + " is doing a wheelie!");
    }
    
    // Getters and setters
    public String getEngineType() { return engineType; }
    public void setEngineType(String engineType) { this.engineType = engineType; }
    
    public boolean isHasSidecar() { return hasSidecar; }
    public void setHasSidecar(boolean hasSidecar) { this.hasSidecar = hasSidecar; }
}

// Another level of inheritance - Truck inherits from Vehicle
@Component
class Truck extends Vehicle {
    private double loadCapacity;
    private int numberOfAxles;
    
    public Truck() {
        super();
    }
    
    public Truck(String brand, String model, int year, double price, double loadCapacity, int numberOfAxles) {
        super(brand, model, year, price);
        this.loadCapacity = loadCapacity;
        this.numberOfAxles = numberOfAxles;
    }
    
    @Override
    public void start() {
        System.out.println("Truck diesel engine starting...");
        super.start();
    }
    
    @Override
    public void displaySpecifications() {
        System.out.println("=== Truck Specifications ===");
        System.out.println("Vehicle: " + toString());
        System.out.println("Load Capacity: " + loadCapacity + " tons");
        System.out.println("Number of Axles: " + numberOfAxles);
    }
    
    public void loadCargo() {
        System.out.println(brand + " " + model + " is loading cargo...");
    }
    
    // Getters and setters
    public double getLoadCapacity() { return loadCapacity; }
    public void setLoadCapacity(double loadCapacity) { this.loadCapacity = loadCapacity; }
    
    public int getNumberOfAxles() { return numberOfAxles; }
    public void setNumberOfAxles(int numberOfAxles) { this.numberOfAxles = numberOfAxles; }
}

// Service class that manages vehicles - demonstrates inheritance in Spring context
@Service
class VehicleService {
    private final List<Vehicle> vehicles;
    
    @Autowired
    public VehicleService(Car car, Motorcycle motorcycle, Truck truck) {
        this.vehicles = new ArrayList<>();
        
        // Initialize vehicles with sample data
        car.setBrand("Toyota");
        car.setModel("Camry");
        car.setYear(2023);
        car.setPrice(25000);
        car.setNumberOfDoors(4);
        car.setFuelType("Gasoline");
        
        motorcycle.setBrand("Harley-Davidson");
        motorcycle.setModel("Street 750");
        motorcycle.setYear(2023);
        motorcycle.setPrice(8000);
        motorcycle.setEngineType("V-Twin");
        motorcycle.setHasSidecar(false);
        
        truck.setBrand("Ford");
        truck.setModel("F-150");
        truck.setYear(2023);
        truck.setPrice(35000);
        truck.setLoadCapacity(2.5);
        truck.setNumberOfAxles(2);
        
        vehicles.add(car);
        vehicles.add(motorcycle);
        vehicles.add(truck);
    }
    
    public void demonstrateInheritance() {
        System.out.println("=== Demonstrating Inheritance with Spring-managed Vehicles ===\n");
        
        for (Vehicle vehicle : vehicles) {
            System.out.println("Processing vehicle: " + vehicle.getClass().getSimpleName());
            
            // Call inherited methods
            vehicle.start();
            vehicle.displaySpecifications();
            
            // Demonstrate polymorphic behavior
            if (vehicle instanceof Car) {
                ((Car) vehicle).honk();
            } else if (vehicle instanceof Motorcycle) {
                ((Motorcycle) vehicle).wheelie();
            } else if (vehicle instanceof Truck) {
                ((Truck) vehicle).loadCargo();
            }
            
            vehicle.stop();
            System.out.println("-------------------\n");
        }
    }
    
    public void showInheritanceBenefits() {
        System.out.println("=== Inheritance Benefits Demonstrated ===");
        System.out.println("• Code reuse: All vehicles inherit common properties and methods");
        System.out.println("• Method overriding: Each vehicle type has its own start() behavior");
        System.out.println("• Abstract methods: Force child classes to implement specific behavior");
        System.out.println("• Polymorphism: Treat different vehicle types uniformly");
        System.out.println("• Spring integration: All classes are managed as Spring beans");
        System.out.println("• Extensibility: Easy to add new vehicle types");
    }
}

@Configuration
@ComponentScan(basePackages = "com.learning.oop")
class InheritanceConfig {
    // Configuration for Spring context
}

public class InheritanceExample {
    public static void main(String[] args) {
        System.out.println("=== Spring Inheritance Example ===\n");
        
        // Create Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(InheritanceConfig.class);
        
        // Get the service bean from Spring container
        VehicleService vehicleService = context.getBean(VehicleService.class);
        
        // Demonstrate inheritance concepts
        vehicleService.demonstrateInheritance();
        vehicleService.showInheritanceBenefits();
    }
} 