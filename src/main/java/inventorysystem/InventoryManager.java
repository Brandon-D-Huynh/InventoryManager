package main.java.inventorysystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;

public class InventoryManager {
    private Map<String, Product> products;
    private int nextId;
    private List<InventoryTransaction> transactions;

    public InventoryManager() {
        products = new HashMap<>();
        transactions = new ArrayList<>();
        nextId = 1;
        addSampleProducts();
    }

    // Add sample products for demonstration
    private void addSampleProducts() {
        addProduct("Laptop", 999.99, 10, "Electronics");
        addProduct("Notebook", 4.99, 100, "Stationery");
        addProduct("Coffee Mug", 12.99, 30, "Kitchenware");
        addProduct("Headphones", 89.99, 15, "Electronics");
        addProduct("Textbook", 59.99, 25, "Books");
    }

    // Add a new product
    public Product addProduct(String name, double price, int quantity, String category) {
        String id = String.format("P%03d", nextId++);
        var product = new Product(id, name, price, quantity, category);
        products.put(id, product);
        logTransaction(TransactionType.ADD, product);
        return product;
    }

    // Update an existing product
    public Optional<Product> updateProduct(String id, String name, double price, int quantity, String category) {
        if (!products.containsKey(id)) {
            return Optional.empty();
        }
        
        var oldProduct = products.get(id);
        var newProduct = new Product(id, name, price, quantity, category);
        products.put(id, newProduct);
        logTransaction(TransactionType.UPDATE, newProduct);
        return Optional.of(newProduct);
    }

    // Delete a product
    public boolean deleteProduct(String id) {
        var product = products.remove(id);
        if (product != null) {
            logTransaction(TransactionType.DELETE, product);
            return true;
        }
        return false;
    }

    // Get a product by ID
    public Optional<Product> getProduct(String id) {
        return Optional.ofNullable(products.get(id));
    }

    // Get all products
    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }

    // Search products by name or category (case-insensitive)
    public List<Product> searchProducts(String query) {
        var lowerQuery = query.toLowerCase();
        
        return products.values().stream()
            .filter(p -> p.name().toLowerCase().contains(lowerQuery) || 
                        p.category().toLowerCase().contains(lowerQuery))
            .collect(Collectors.toList());
    }
    
    // Log transactions for audit purposes
    private void logTransaction(TransactionType type, Product product) {
        transactions.add(new InventoryTransaction(
            LocalDateTime.now(),
            type,
            product
        ));
    }
    
    // Get transaction history
    public List<InventoryTransaction> getTransactionHistory() {
        return List.copyOf(transactions);
    }
}