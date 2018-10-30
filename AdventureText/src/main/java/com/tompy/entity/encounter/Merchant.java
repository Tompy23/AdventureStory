package com.tompy.entity.encounter;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;

import java.util.List;

/**
 * A Merchant is a special NPC Encounter where a player can buy and sell items.
 */
public interface Merchant extends Entity, MerchantStateMachine {

    /**
     * Retrieve a list of items the Merchant has for sale
     *
     * @return
     */
    List<Item> getAvailable();

    /**
     * Retrieve the State for Buying
     *
     * @return
     */
    MerchantState getBuyState();

    /**
     * Retrieve the state for Selling
     *
     * @return
     */
    MerchantState getSellState();

    /**
     * Retrieve the state for chatting, which is what a normal NPC does
     *
     * @return
     */
    MerchantState getChatState();

    /**
     * Get the player object
     *
     * @return
     */
    Player getPlayer();

    /**
     * Get the adventure object
     *
     * @return
     */
    Adventure getAdventure();

    /**
     * Get the sell rate for the merchant, this is the factor applied to values when player sells the item.
     *
     * @return
     */
    double getSellRate();

    /**
     * Get the buy rate for the merchant, this is the factor applied to values when player buys the item.
     * @return
     */
    double getBuyRate();

    /**
     * Add an item to the merchant's list
     *
     * @param item
     */
    void addItem(Item item);

    /**
     * Remove an item from the merchant's list
     *
     * @param item
     */
    void removeItem(Item item);
}
