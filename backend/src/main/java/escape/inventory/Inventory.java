
package main.java.escape.inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the items collected by the player.
 */
public class Inventory {

    private final List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        if (item == null) {
            return false;
        }

        for (Item existingItem : items) {
            if (existingItem.getId().equals(item.getId())) {
                return false;
            }
        }

        items.add(item);
        return true;
    }

    public boolean removeItem(String itemId) {
        if (itemId == null) {
            return false;
        }

        return items.removeIf(item -> item.getId().equals(itemId));
    }

    public List<Item> getItems() {
        return List.copyOf(items);
    }
}

