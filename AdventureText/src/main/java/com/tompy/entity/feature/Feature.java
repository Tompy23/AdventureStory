package com.tompy.entity.feature;

import com.tompy.adventure.Adventure;
import com.tompy.entity.compartment.Compartment;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

/**
 * An immovable interactive thing in an Area
 */
public interface Feature extends Compartment {

    /**
     * Search the feature for more information
     *
     * @param player
     * @param adventure
     * @return
     */
    List<Response> search(Player player, Adventure adventure);

    /**
     * Put the Feature in Open state
     *
     * @param player
     * @param adventure
     * @return
     */
    List<Response> open(Player player, Adventure adventure);

    /**
     * Remove the Open state from the Feature
     *
     * @param player
     * @param adventure
     * @return
     */
    List<Response> close(Player player, Adventure adventure);

    /**
     * Add Locked State to Feature
     *
     * @param player
     * @param adventure
     * @return
     */
    List<Response> lock(Player player, Adventure adventure);

    /**
     * Remove Locked State from Feature
     *
     * @param player
     * @param adventure
     * @return
     */
    List<Response> unlock(Player player, Adventure adventure);

    /**
     * Alert that the Feature was not used properly somehow with a specific Item.
     *
     * @param item
     * @param player
     * @param adventure
     * @return
     */
    List<Response> misUse(Item item, Player player, Adventure adventure);

    /**
     * Drink from the feature (a fountain for example)
     *
     * @param player
     * @param adventure
     * @return
     */
    List<Response> drink(Player player, Adventure adventure);
}
