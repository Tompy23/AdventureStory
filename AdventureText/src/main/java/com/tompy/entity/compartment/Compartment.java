package com.tompy.entity.compartment;

import com.tompy.entity.Entity;
import com.tompy.entity.item.Item;

import java.util.List;

/**
 * An interface allowing an Entity to contain Items
 */
public interface Compartment extends Entity {
    List<Item> getAllItems();

    void addItem(Item item);

    void removeItem(Item item);

}
