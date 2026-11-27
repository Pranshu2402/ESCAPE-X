
package main.java.escape.inventory;

/**
 * Simple test program for the Inventory class.
 */
public class InventoryTest {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        Item key = new Item(
                "item001",
                "Rusty Key",
                "A rusty key found in the hallway.",
                "key"
        );

        Item note = new Item(
                "item002",
                "Secret Note",
                "A note with a mysterious code.",
                "clue"
        );

        // Test adding items
        System.out.println("Adding key: " + inventory.addItem(key));
        System.out.println("Adding note: " + inventory.addItem(note));

        // Test duplicate prevention
        System.out.println("Adding duplicate key: " + inventory.addItem(key));

        // Display inventory
        System.out.println("\nItems in inventory:");
        for (Item item : inventory.getItems()) {
            System.out.println(item.getName() + " - " + item.getDescription());
        }

        // Test removing an item
        System.out.println("\nRemoving key: " + inventory.removeItem("item001"));

        // Display inventory after removal
        System.out.println("\nInventory after removal:");
        for (Item item : inventory.getItems()) {
            System.out.println(item.getName());
        }
    }
}
