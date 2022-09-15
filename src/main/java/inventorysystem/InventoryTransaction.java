package main.java.inventorysystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Record for tracking inventory transactions
public record InventoryTransaction(
    LocalDateTime timestamp,
    TransactionType type,
    Product product
) {
    @Override
    public String toString() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: %s - %s (Qty: %d, Price: $%.2f)",
            timestamp.format(formatter),
            type,
            product.id(),
            product.name(),
            product.quantity(),
            product.price());
    }
}