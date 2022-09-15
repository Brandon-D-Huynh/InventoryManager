package main.java.inventorysystem;

import java.util.Scanner;
import java.util.List;

public class UserInterface {
    private final InventoryManager manager;
    private final Scanner scanner;
    private boolean running;

    public UserInterface(InventoryManager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
        this.running = false;
    }

    public void start() {
        running = true;
        displayWelcomeMessage();
        
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            processChoice(choice);
        }
    }

    private void displayWelcomeMessage() {
        System.out.println("""
            ===================================
            INVENTORY MANAGEMENT SYSTEM
            Java 20 Student Portfolio Project
            ===================================
            """);
    }

    private void displayMenu() {
        System.out.println("""
            
            MAIN MENU:
            1. View All Products
            2. Search Products
            3. Add New Product
            4. Update Product
            5. Delete Product
            6. View Transaction History
            7. Exit
            """);
        System.out.print("Enter your choice (1-7): ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1 -> viewAllProducts();
            case 2 -> searchProducts();
            case 3 -> addProduct();
            case 4 -> updateProduct();
            case 5 -> deleteProduct();
            case 6 -> viewTransactionHistory();
            case 7 -> exit();
            default -> System.out.println("Invalid choice. Please try again.");
        }
        
        // Pause before returning to menu
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void viewAllProducts() {
        System.out.println("\n=== ALL PRODUCTS ===");
        
        var products = manager.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        
        displayProducts(products);
    }

    private void searchProducts() {
        System.out.print("\nEnter search term (name or category): ");
        var query = scanner.nextLine().trim();
        
        var results = manager.searchProducts(query);
        
        System.out.println("\n=== SEARCH RESULTS ===");
        if (results.isEmpty()) {
            System.out.println("No products found matching '" + query + "'");
            return;
        }
        
        displayProducts(results);
    }

    private void displayProducts(List<Product> products) {
        for (var product : products) {
            System.out.println(product);
        }
        System.out.println("Total products: " + products.size());
    }

    private void addProduct() {
        System.out.println("\n=== ADD NEW PRODUCT ===");
        
        System.out.print("Enter product name: ");
        var name = scanner.nextLine().trim();
        
        double price = getDoubleInput("Enter product price: $");
        int quantity = getIntInput("Enter product quantity: ");
        
        System.out.print("Enter product category: ");
        var category = scanner.nextLine().trim();
        
        var product = manager.addProduct(name, price, quantity, category);
        System.out.println("\nProduct added successfully:");
        System.out.println(product);
    }

    private void updateProduct() {
        System.out.println("\n=== UPDATE PRODUCT ===");
        
        System.out.print("Enter product ID to update: ");
        var id = scanner.nextLine().trim();
        
        var optionalProduct = manager.getProduct(id);
        if (optionalProduct.isEmpty()) {
            System.out.println("Product not found with ID: " + id);
            return;
        }
        
        var product = optionalProduct.get();
        System.out.println("Current product details:");
        System.out.println(product);
        
        System.out.print("\nEnter new name (press Enter to keep '" + product.name() + "'): ");
        var name = scanner.nextLine().trim();
        if (name.isEmpty()) name = product.name();
        
        var price = product.price();
        System.out.print("Enter new price (press Enter to keep $" + price + "): ");
        var priceInput = scanner.nextLine().trim();
        if (!priceInput.isEmpty()) {
            try {
                price = Double.parseDouble(priceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Keeping the current price.");
            }
        }
        
        var quantity = product.quantity();
        System.out.print("Enter new quantity (press Enter to keep " + quantity + "): ");
        var quantityInput = scanner.nextLine().trim();
        if (!quantityInput.isEmpty()) {
            try {
                quantity = Integer.parseInt(quantityInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Keeping the current quantity.");
            }
        }
        
        System.out.print("Enter new category (press Enter to keep '" + product.category() + "'): ");
        var category = scanner.nextLine().trim();
        if (category.isEmpty()) category = product.category();
        
        var updatedProduct = manager.updateProduct(id, name, price, quantity, category);
        if (updatedProduct.isPresent()) {
            System.out.println("\nProduct updated successfully:");
            System.out.println(updatedProduct.get());
        } else {
            System.out.println("Error updating product.");
        }
    }

    private void deleteProduct() {
        System.out.println("\n=== DELETE PRODUCT ===");
        
        System.out.print("Enter product ID to delete: ");
        var id = scanner.nextLine().trim();
        
        var optionalProduct = manager.getProduct(id);
        if (optionalProduct.isEmpty()) {
            System.out.println("Product not found with ID: " + id);
            return;
        }
        
        System.out.println("Product to delete:");
        System.out.println(optionalProduct.get());
        
        System.out.print("Are you sure you want to delete this product? (y/n): ");
        var confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            boolean success = manager.deleteProduct(id);
            if (success) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Error deleting product.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private void viewTransactionHistory() {
        System.out.println("\n=== TRANSACTION HISTORY ===");
        
        var transactions = manager.getTransactionHistory();
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }
        
        for (var transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("Total transactions: " + transactions.size());
    }

    private void exit() {
        System.out.println("\nThank you for using the Inventory Management System!");
        running = false;
    }
    
    private double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}