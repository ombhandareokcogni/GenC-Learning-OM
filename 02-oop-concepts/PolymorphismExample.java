package com.learning.oop;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;
import java.util.Arrays;

/**
 * Polymorphism Example using Spring
 * Demonstrates runtime polymorphism through interfaces and Spring's dependency injection
 */

// Interface defining contract for payment processing
interface PaymentProcessor {
    boolean processPayment(double amount, String currency);
    String getPaymentMethod();
    double getTransactionFee(double amount);
    boolean supportsRefund();
}

// Implementation 1 - Credit Card Payment
@Component
@Qualifier("creditCard")
class CreditCardProcessor implements PaymentProcessor {
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing Credit Card payment...");
        System.out.printf("Charging %.2f %s to credit card%n", amount, currency);
        
        // Simulate processing
        try {
            Thread.sleep(1000);
            System.out.println("✓ Credit Card payment successful!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("✗ Credit Card payment failed!");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return amount * 0.029; // 2.9% fee
    }
    
    @Override
    public boolean supportsRefund() {
        return true;
    }
}

// Implementation 2 - PayPal Payment
@Component
@Qualifier("paypal")
class PayPalProcessor implements PaymentProcessor {
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing PayPal payment...");
        System.out.printf("Transferring %.2f %s via PayPal%n", amount, currency);
        
        // Simulate processing
        try {
            Thread.sleep(800);
            System.out.println("✓ PayPal payment successful!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("✗ PayPal payment failed!");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return amount * 0.035; // 3.5% fee
    }
    
    @Override
    public boolean supportsRefund() {
        return true;
    }
}

// Implementation 3 - Bank Transfer Payment
@Component
@Qualifier("bankTransfer")
@Primary // This will be the default implementation when no qualifier is specified
class BankTransferProcessor implements PaymentProcessor {
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing Bank Transfer payment...");
        System.out.printf("Initiating bank transfer for %.2f %s%n", amount, currency);
        
        // Simulate processing
        try {
            Thread.sleep(1500);
            System.out.println("✓ Bank Transfer payment successful!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("✗ Bank Transfer payment failed!");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return 2.50; // Fixed fee
    }
    
    @Override
    public boolean supportsRefund() {
        return false; // Bank transfers typically don't support easy refunds
    }
}

// Implementation 4 - Cryptocurrency Payment
@Component
@Qualifier("crypto")
class CryptocurrencyProcessor implements PaymentProcessor {
    
    @Override
    public boolean processPayment(double amount, String currency) {
        System.out.println("Processing Cryptocurrency payment...");
        System.out.printf("Sending %.2f %s worth of Bitcoin%n", amount, currency);
        
        // Simulate processing
        try {
            Thread.sleep(1200);
            System.out.println("✓ Cryptocurrency payment successful!");
            return true;
        } catch (InterruptedException e) {
            System.err.println("✗ Cryptocurrency payment failed!");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "Cryptocurrency";
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return 0.001 * amount; // 0.1% fee
    }
    
    @Override
    public boolean supportsRefund() {
        return false; // Crypto transactions are typically irreversible
    }
}

// Data class for transaction
class Transaction {
    private final double amount;
    private final String currency;
    private final String description;
    
    public Transaction(double amount, String currency, String description) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }
    
    // Getters
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getDescription() { return description; }
    
    @Override
    public String toString() {
        return String.format("Transaction{amount=%.2f, currency='%s', description='%s'}", 
                           amount, currency, description);
    }
}

// Service class demonstrating polymorphism
@Service
class PaymentService {
    
    // Different ways to inject polymorphic dependencies
    private final PaymentProcessor defaultProcessor;
    private final PaymentProcessor creditCardProcessor;
    private final PaymentProcessor paypalProcessor;
    private final PaymentProcessor bankTransferProcessor;
    private final PaymentProcessor cryptoProcessor;
    
    // Constructor injection with qualifiers
    @Autowired
    public PaymentService(
        PaymentProcessor defaultProcessor, // @Primary annotation makes this the default
        @Qualifier("creditCard") PaymentProcessor creditCardProcessor,
        @Qualifier("paypal") PaymentProcessor paypalProcessor,
        @Qualifier("bankTransfer") PaymentProcessor bankTransferProcessor,
        @Qualifier("crypto") PaymentProcessor cryptoProcessor) {
        
        this.defaultProcessor = defaultProcessor;
        this.creditCardProcessor = creditCardProcessor;
        this.paypalProcessor = paypalProcessor;
        this.bankTransferProcessor = bankTransferProcessor;
        this.cryptoProcessor = cryptoProcessor;
    }
    
    // Method demonstrating runtime polymorphism
    public void processTransaction(Transaction transaction, String paymentMethod) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Processing: " + transaction);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("=".repeat(50));
        
        PaymentProcessor processor = getProcessorByMethod(paymentMethod);
        
        if (processor != null) {
            // Calculate and display fees
            double fee = processor.getTransactionFee(transaction.getAmount());
            double totalAmount = transaction.getAmount() + fee;
            
            System.out.printf("Transaction Fee: %.2f %s%n", fee, transaction.getCurrency());
            System.out.printf("Total Amount: %.2f %s%n", totalAmount, transaction.getCurrency());
            System.out.println("Refund Support: " + (processor.supportsRefund() ? "Yes" : "No"));
            System.out.println();
            
            // Process payment - demonstrates polymorphic method call
            boolean success = processor.processPayment(totalAmount, transaction.getCurrency());
            
            if (success) {
                System.out.println("Transaction completed successfully! ✓");
            } else {
                System.out.println("Transaction failed! ✗");
            }
        } else {
            System.err.println("Unknown payment method: " + paymentMethod);
        }
    }
    
    // Method to get processor based on payment method - demonstrates polymorphism
    private PaymentProcessor getProcessorByMethod(String method) {
        switch (method.toLowerCase()) {
            case "credit card":
            case "creditcard":
                return creditCardProcessor;
            case "paypal":
                return paypalProcessor;
            case "bank transfer":
            case "banktransfer":
                return bankTransferProcessor;
            case "crypto":
            case "cryptocurrency":
                return cryptoProcessor;
            case "default":
                return defaultProcessor;
            default:
                return null;
        }
    }
    
    // Method demonstrating polymorphism with collections
    public void comparePaymentMethods(double amount) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PAYMENT METHOD COMPARISON FOR AMOUNT: $" + amount);
        System.out.println("=".repeat(60));
        
        List<PaymentProcessor> processors = Arrays.asList(
            creditCardProcessor, paypalProcessor, bankTransferProcessor, cryptoProcessor
        );
        
        for (PaymentProcessor processor : processors) {
            double fee = processor.getTransactionFee(amount);
            double total = amount + fee;
            
            System.out.printf("%-15s | Fee: $%6.2f | Total: $%7.2f | Refund: %s%n",
                processor.getPaymentMethod(),
                fee,
                total,
                processor.supportsRefund() ? "Yes" : "No"
            );
        }
        System.out.println("=".repeat(60));
    }
    
    public void demonstratePolymorphism() {
        System.out.println("=== Polymorphism Benefits ===");
        System.out.println("• Same interface (PaymentProcessor) - different implementations");
        System.out.println("• Runtime method resolution - behavior determined at runtime");
        System.out.println("• Easy to add new payment methods without changing existing code");
        System.out.println("• Spring manages all implementations as beans");
        System.out.println("• Dependency injection allows flexible switching between implementations");
        System.out.println("• Code is extensible and maintainable");
    }
}

@Configuration
@ComponentScan(basePackages = "com.learning.oop")
class PolymorphismConfig {
    // Configuration for Spring context
}

public class PolymorphismExample {
    public static void main(String[] args) {
        System.out.println("=== Spring Polymorphism Example ===\n");
        
        // Create Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(PolymorphismConfig.class);
        
        // Get the service bean from Spring container
        PaymentService paymentService = context.getBean(PaymentService.class);
        
        // Create sample transactions
        Transaction[] transactions = {
            new Transaction(99.99, "USD", "Premium Software License"),
            new Transaction(250.00, "EUR", "Online Course"),
            new Transaction(1500.00, "USD", "Laptop Purchase"),
            new Transaction(50.00, "GBP", "Monthly Subscription")
        };
        
        String[] paymentMethods = {"Credit Card", "PayPal", "Bank Transfer", "Crypto"};
        
        // Demonstrate polymorphism in action
        for (int i = 0; i < transactions.length; i++) {
            paymentService.processTransaction(transactions[i], paymentMethods[i]);
        }
        
        // Compare payment methods
        paymentService.comparePaymentMethods(100.00);
        
        // Show polymorphism benefits
        System.out.println();
        paymentService.demonstratePolymorphism();
    }
} 