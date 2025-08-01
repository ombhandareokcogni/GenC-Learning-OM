package com.learning.patterns.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Proxy Pattern Example
 * 
 * Purpose: Provides a placeholder/surrogate for another object to control access to it
 * Types of Proxies:
 * - Virtual Proxy: Controls access to expensive objects (lazy loading)
 * - Protection Proxy: Controls access based on permissions
 * - Remote Proxy: Represents objects in different address spaces
 * - Caching Proxy: Caches results to improve performance
 */

// Step 1: Subject interface - common interface for RealSubject and Proxy
interface DocumentService {
    String loadDocument(String documentId);
    void saveDocument(String documentId, String content);
    List<String> listDocuments();
    void deleteDocument(String documentId);
}

// Step 2: RealSubject - the actual expensive object
class RealDocumentService implements DocumentService {
    
    private Map<String, String> documents = new HashMap<>();
    
    public RealDocumentService() {
        // Simulate expensive initialization
        System.out.println("🏗️ [REAL SERVICE] Initializing expensive DocumentService...");
        try {
            Thread.sleep(2000); // Simulate database connection setup
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Load some sample documents
        documents.put("DOC001", "This is the content of Document 1");
        documents.put("DOC002", "This is the content of Document 2");
        documents.put("DOC003", "This is the content of Document 3");
        
        System.out.println("✅ [REAL SERVICE] DocumentService initialized successfully!");
    }
    
    @Override
    public String loadDocument(String documentId) {
        System.out.println("📄 [REAL SERVICE] Loading document: " + documentId);
        
        // Simulate network delay
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String content = documents.get(documentId);
        if (content != null) {
            System.out.println("✅ [REAL SERVICE] Document loaded successfully");
            return content;
        } else {
            System.out.println("❌ [REAL SERVICE] Document not found: " + documentId);
            return null;
        }
    }
    
    @Override
    public void saveDocument(String documentId, String content) {
        System.out.println("💾 [REAL SERVICE] Saving document: " + documentId);
        
        // Simulate save delay
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        documents.put(documentId, content);
        System.out.println("✅ [REAL SERVICE] Document saved successfully");
    }
    
    @Override
    public List<String> listDocuments() {
        System.out.println("📋 [REAL SERVICE] Listing all documents");
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return new ArrayList<>(documents.keySet());
    }
    
    @Override
    public void deleteDocument(String documentId) {
        System.out.println("🗑️ [REAL SERVICE] Deleting document: " + documentId);
        
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        documents.remove(documentId);
        System.out.println("✅ [REAL SERVICE] Document deleted successfully");
    }
}

// Step 3: Virtual Proxy - Lazy loading proxy
class VirtualDocumentProxy implements DocumentService {
    
    private RealDocumentService realService; // Lazy-loaded
    
    public VirtualDocumentProxy() {
        System.out.println("🎭 [VIRTUAL PROXY] Created (real service not yet initialized)");
    }
    
    // Lazy initialization - create real service only when needed
    private RealDocumentService getRealService() {
        if (realService == null) {
            System.out.println("🎭 [VIRTUAL PROXY] First access - initializing real service...");
            realService = new RealDocumentService();
        }
        return realService;
    }
    
    @Override
    public String loadDocument(String documentId) {
        System.out.println("🎭 [VIRTUAL PROXY] Load request received for: " + documentId);
        return getRealService().loadDocument(documentId);
    }
    
    @Override
    public void saveDocument(String documentId, String content) {
        System.out.println("🎭 [VIRTUAL PROXY] Save request received for: " + documentId);
        getRealService().saveDocument(documentId, content);
    }
    
    @Override
    public List<String> listDocuments() {
        System.out.println("🎭 [VIRTUAL PROXY] List request received");
        return getRealService().listDocuments();
    }
    
    @Override
    public void deleteDocument(String documentId) {
        System.out.println("🎭 [VIRTUAL PROXY] Delete request received for: " + documentId);
        getRealService().deleteDocument(documentId);
    }
}

// Step 4: Protection Proxy - Access control proxy
class ProtectionDocumentProxy implements DocumentService {
    
    private RealDocumentService realService;
    private String currentUser;
    private Map<String, String> userRoles;
    
    public ProtectionDocumentProxy(String currentUser) {
        this.currentUser = currentUser;
        this.realService = new RealDocumentService();
        
        // Setup user roles
        this.userRoles = new HashMap<>();
        userRoles.put("admin", "ADMIN");
        userRoles.put("editor", "EDITOR");
        userRoles.put("viewer", "VIEWER");
        userRoles.put("guest", "GUEST");
        
        System.out.println("🛡️ [PROTECTION PROXY] Created for user: " + currentUser + 
                          " (Role: " + userRoles.getOrDefault(currentUser, "UNKNOWN") + ")");
    }
    
    private boolean hasPermission(String operation) {
        String role = userRoles.getOrDefault(currentUser, "GUEST");
        
        switch (operation) {
            case "READ":
                return !role.equals("GUEST");
            case "WRITE":
                return role.equals("ADMIN") || role.equals("EDITOR");
            case "DELETE":
                return role.equals("ADMIN");
            default:
                return false;
        }
    }
    
    @Override
    public String loadDocument(String documentId) {
        System.out.println("🛡️ [PROTECTION PROXY] Access check for READ operation");
        
        if (hasPermission("READ")) {
            System.out.println("✅ [PROTECTION PROXY] Access granted");
            return realService.loadDocument(documentId);
        } else {
            System.out.println("❌ [PROTECTION PROXY] Access denied - insufficient permissions");
            return "ACCESS DENIED: You don't have permission to read documents";
        }
    }
    
    @Override
    public void saveDocument(String documentId, String content) {
        System.out.println("🛡️ [PROTECTION PROXY] Access check for WRITE operation");
        
        if (hasPermission("WRITE")) {
            System.out.println("✅ [PROTECTION PROXY] Access granted");
            realService.saveDocument(documentId, content);
        } else {
            System.out.println("❌ [PROTECTION PROXY] Access denied - insufficient permissions");
        }
    }
    
    @Override
    public List<String> listDocuments() {
        System.out.println("🛡️ [PROTECTION PROXY] Access check for READ operation");
        
        if (hasPermission("READ")) {
            System.out.println("✅ [PROTECTION PROXY] Access granted");
            return realService.listDocuments();
        } else {
            System.out.println("❌ [PROTECTION PROXY] Access denied - insufficient permissions");
            return new ArrayList<>();
        }
    }
    
    @Override
    public void deleteDocument(String documentId) {
        System.out.println("🛡️ [PROTECTION PROXY] Access check for DELETE operation");
        
        if (hasPermission("DELETE")) {
            System.out.println("✅ [PROTECTION PROXY] Access granted");
            realService.deleteDocument(documentId);
        } else {
            System.out.println("❌ [PROTECTION PROXY] Access denied - insufficient permissions");
        }
    }
}

// Step 5: Caching Proxy - Performance optimization proxy
class CachingDocumentProxy implements DocumentService {
    
    private RealDocumentService realService;
    private Map<String, String> cache;
    private Map<String, Long> cacheTimestamps;
    private static final long CACHE_EXPIRY = 5000; // 5 seconds
    
    public CachingDocumentProxy() {
        this.realService = new RealDocumentService();
        this.cache = new HashMap<>();
        this.cacheTimestamps = new HashMap<>();
        System.out.println("💾 [CACHING PROXY] Created with cache");
    }
    
    private boolean isCacheValid(String documentId) {
        Long timestamp = cacheTimestamps.get(documentId);
        if (timestamp == null) return false;
        
        return (System.currentTimeMillis() - timestamp) < CACHE_EXPIRY;
    }
    
    @Override
    public String loadDocument(String documentId) {
        System.out.println("💾 [CACHING PROXY] Load request for: " + documentId);
        
        // Check cache first
        if (cache.containsKey(documentId) && isCacheValid(documentId)) {
            System.out.println("🎯 [CACHING PROXY] Cache HIT - returning cached content");
            return cache.get(documentId);
        }
        
        System.out.println("❌ [CACHING PROXY] Cache MISS - fetching from real service");
        String content = realService.loadDocument(documentId);
        
        // Cache the result
        if (content != null) {
            cache.put(documentId, content);
            cacheTimestamps.put(documentId, System.currentTimeMillis());
            System.out.println("💾 [CACHING PROXY] Content cached for: " + documentId);
        }
        
        return content;
    }
    
    @Override
    public void saveDocument(String documentId, String content) {
        System.out.println("💾 [CACHING PROXY] Save request for: " + documentId);
        realService.saveDocument(documentId, content);
        
        // Update cache
        cache.put(documentId, content);
        cacheTimestamps.put(documentId, System.currentTimeMillis());
        System.out.println("💾 [CACHING PROXY] Cache updated for: " + documentId);
    }
    
    @Override
    public List<String> listDocuments() {
        System.out.println("💾 [CACHING PROXY] List request - bypassing cache for consistency");
        return realService.listDocuments();
    }
    
    @Override
    public void deleteDocument(String documentId) {
        System.out.println("💾 [CACHING PROXY] Delete request for: " + documentId);
        realService.deleteDocument(documentId);
        
        // Remove from cache
        cache.remove(documentId);
        cacheTimestamps.remove(documentId);
        System.out.println("💾 [CACHING PROXY] Removed from cache: " + documentId);
    }
}

// Step 6: Remote Proxy - Simulates remote service access
class RemoteDocumentProxy implements DocumentService {
    
    private String serverUrl;
    private RealDocumentService realService; // Simulating remote service
    
    public RemoteDocumentProxy(String serverUrl) {
        this.serverUrl = serverUrl;
        this.realService = new RealDocumentService(); // In real scenario, this would be HTTP client
        System.out.println("🌐 [REMOTE PROXY] Connected to server: " + serverUrl);
    }
    
    private void simulateNetworkCall(String operation) {
        System.out.println("🌐 [REMOTE PROXY] Making remote call: " + operation + " to " + serverUrl);
        try {
            Thread.sleep(100); // Simulate network latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public String loadDocument(String documentId) {
        simulateNetworkCall("GET /documents/" + documentId);
        return realService.loadDocument(documentId);
    }
    
    @Override
    public void saveDocument(String documentId, String content) {
        simulateNetworkCall("PUT /documents/" + documentId);
        realService.saveDocument(documentId, content);
    }
    
    @Override
    public List<String> listDocuments() {
        simulateNetworkCall("GET /documents");
        return realService.listDocuments();
    }
    
    @Override
    public void deleteDocument(String documentId) {
        simulateNetworkCall("DELETE /documents/" + documentId);
        realService.deleteDocument(documentId);
    }
}

// Main class - demonstrates all proxy types
public class ProxyPatternExample {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("                    PROXY PATTERN DEMONSTRATION");
        System.out.println("=".repeat(70));
        
        // Demonstrate Virtual Proxy
        demonstrateVirtualProxy();
        
        // Demonstrate Protection Proxy
        demonstrateProtectionProxy();
        
        // Demonstrate Caching Proxy
        demonstrateCachingProxy();
        
        // Demonstrate Remote Proxy
        demonstrateRemoteProxy();
        
        // Show proxy benefits
        showProxyBenefits();
    }
    
    private static void demonstrateVirtualProxy() {
        System.out.println("\n🎭 1. VIRTUAL PROXY DEMONSTRATION");
        System.out.println("-".repeat(50));
        System.out.println("Purpose: Lazy loading - defer expensive object creation until needed");
        
        // Create proxy (fast - no expensive initialization yet)
        DocumentService proxy = new VirtualDocumentProxy();
        
        System.out.println("\n⏱️ Proxy created instantly! Real service not yet initialized.");
        System.out.println("Now making first request (this will trigger real service creation):");
        
        // First access triggers real service creation
        String content = proxy.loadDocument("DOC001");
        System.out.println("Content: " + content);
        
        System.out.println("\nSubsequent calls use the already-initialized real service:");
        proxy.saveDocument("DOC004", "New document content");
    }
    
    private static void demonstrateProtectionProxy() {
        System.out.println("\n\n🛡️ 2. PROTECTION PROXY DEMONSTRATION");
        System.out.println("-".repeat(50));
        System.out.println("Purpose: Access control based on user permissions");
        
        // Test different user roles
        String[] users = {"admin", "editor", "viewer", "guest"};
        
        for (String user : users) {
            System.out.println("\n--- Testing user: " + user + " ---");
            DocumentService proxy = new ProtectionDocumentProxy(user);
            
            // Try different operations
            proxy.loadDocument("DOC001");
            proxy.saveDocument("DOC005", "Attempting to save...");
            proxy.deleteDocument("DOC002");
        }
    }
    
    private static void demonstrateCachingProxy() {
        System.out.println("\n\n💾 3. CACHING PROXY DEMONSTRATION");
        System.out.println("-".repeat(50));
        System.out.println("Purpose: Performance optimization through caching");
        
        DocumentService proxy = new CachingDocumentProxy();
        
        System.out.println("\n--- First access (cache miss) ---");
        long start1 = System.currentTimeMillis();
        String content1 = proxy.loadDocument("DOC001");
        long time1 = System.currentTimeMillis() - start1;
        System.out.println("Time taken: " + time1 + "ms");
        
        System.out.println("\n--- Second access (cache hit) ---");
        long start2 = System.currentTimeMillis();
        String content2 = proxy.loadDocument("DOC001");
        long time2 = System.currentTimeMillis() - start2;
        System.out.println("Time taken: " + time2 + "ms");
        System.out.println("Performance improvement: " + ((time1 - time2) * 100 / time1) + "%");
        
        System.out.println("\n--- Testing cache invalidation ---");
        proxy.saveDocument("DOC001", "Updated content");
        proxy.loadDocument("DOC001"); // Should get updated content from cache
    }
    
    private static void demonstrateRemoteProxy() {
        System.out.println("\n\n🌐 4. REMOTE PROXY DEMONSTRATION");
        System.out.println("-".repeat(50));
        System.out.println("Purpose: Represents object in different address space (remote server)");
        
        DocumentService proxy = new RemoteDocumentProxy("https://api.documents.com");
        
        System.out.println("\nMaking remote calls through proxy:");
        proxy.listDocuments();
        proxy.loadDocument("DOC001");
        proxy.saveDocument("DOC006", "Remote document content");
    }
    
    private static void showProxyBenefits() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                        PROXY PATTERN BENEFITS");
        System.out.println("=".repeat(70));
        
        System.out.println("🎭 VIRTUAL PROXY BENEFITS:");
        System.out.println("  • Lazy initialization - create expensive objects only when needed");
        System.out.println("  • Improved application startup time");
        System.out.println("  • Memory optimization");
        
        System.out.println("\n🛡️ PROTECTION PROXY BENEFITS:");
        System.out.println("  • Access control - enforce security policies");
        System.out.println("  • Authentication and authorization");
        System.out.println("  • Audit logging of access attempts");
        
        System.out.println("\n💾 CACHING PROXY BENEFITS:");
        System.out.println("  • Performance optimization through caching");
        System.out.println("  • Reduced load on expensive resources");
        System.out.println("  • Automatic cache management");
        
        System.out.println("\n🌐 REMOTE PROXY BENEFITS:");
        System.out.println("  • Location transparency - hide remote complexity");
        System.out.println("  • Network optimization - connection pooling, batching");
        System.out.println("  • Fault tolerance - retry logic, fallback mechanisms");
        
        System.out.println("\n📋 COMMON PROXY IMPLEMENTATIONS:");
        System.out.println("  • Java Dynamic Proxy");
        System.out.println("  • Spring AOP Proxies");
        System.out.println("  • Hibernate Lazy Loading Proxies");
        System.out.println("  • HTTP Proxy Servers");
        System.out.println("  • CDN (Content Delivery Networks)");
        
        System.out.println("\n⚠️ CONSIDERATIONS:");
        System.out.println("  • Adds complexity - extra layer of indirection");
        System.out.println("  • Potential performance overhead");
        System.out.println("  • Memory usage for caching proxies");
        System.out.println("  • Debugging can be more complex");
        
        System.out.println("=".repeat(70));
    }
} 