package com.tompy.player;

import com.tompy.entity.area.Area;
import com.tompy.entity.compartment.Compartment;
import com.tompy.entity.item.Item;

import java.util.List;

/**
 * The player in the game
 */
public interface Player {

    /**
     * Get player's name
     *
     * @return
     */
    String getName();

    /**
     * Get the current area
     *
     * @return
     */
    Area getArea();

    /**
     * Set the current area
     *
     * @param area
     */
    void setArea(Area area);

    /**
     * Mark that an ara was visited
     *
     * @param areaName
     */
    void visitArea(String areaName);

    /**
     * Get the area visit count for that player
     *
     * @param roomName
     * @return
     */
    int areaVisitCount(String roomName);

    /**
     * Mark that a player searched the area
     *
     * @param roomName
     */
    void searchArea(String roomName);

    /**
     * Get the area search count for the player
     *
     * @param roomName
     * @return
     */
    int areaSearchCount(String roomName);

    /**
     * Add an item to the player's inventory
     *
     * @param item
     * @return
     */
    boolean addItem(Item item);

    /**
     * Remove an item from the player's inventory
     *
     * @param item
     */
    void removeItem(Item item);

    /**
     * Add an item into a Compartment and remove from player inventory
     *
     * @param item
     * @param compartment
     * @return
     */
    boolean dropItem(Item item, Compartment compartment);

    /**
     * Put the item in hand for use (not implemented)
     *
     * @param item
     * @return
     */
    boolean equip(Item item);

    /**
     * Remove the item from the hand for use (not implemented)
     *
     * @param item
     * @return
     */
    boolean unequip(Item item);

    /**
     * Return all items in inventory
     *
     * @return
     */
    List<Item> getInventory();

    /**
     * Get the money the player has
     *
     * @return
     */
    int moneyValue();

    /**
     * Add money to player
     *
     * @param value
     */
    void addMoney(int value);

    /**
     * Take money out, return false if not enough
     *
     * @param value
     * @return
     */
    boolean pay(int value);
}
