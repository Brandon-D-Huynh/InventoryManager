package main.java.inventorysystem;

public class InventoryManagementSystem {
    public static void main(String[] args) {
        var manager = new InventoryManager();
        var ui = new UserInterface(manager);
        ui.start();
    }
}