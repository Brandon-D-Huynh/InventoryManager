package main.java.inventorysystem;

// Product record with immutable data (Java 14+ feature)
public record Product(String id, 
               String name, 
               double price, 
               int quantity, 
               String category) {
    
    // Factory method to create updated products
    public Product withUpdatedQuantity(int newQuantity) {
        return new Product(id, name, price, newQuantity, category);
    }
    
    public Product withUpdatedPrice(double newPrice) {
        return new Product(id, name, newPrice, quantity, category);
    }
    
    public Product withUpdatedName(String newName) {
        return new Product(id, newName, price, quantity, category);
    }
    
    public Product withUpdatedCategory(String newCategory) {
        return new Product(id, name, price, quantity, newCategory);
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Price: $%.2f | Quantity: %d | Category: %s",
                id, name, price, quantity, category);
    }
}