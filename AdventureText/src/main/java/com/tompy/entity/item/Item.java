package com.tompy.entity.item;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.entity.feature.Feature;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

/**
 * Hold state for an item
 * Funcionaly an Item is USED on a TARGET (another Item or Feature) and the action is taken with in the Use function.
 * <p>
 * NOTE
 * Ok, let's try this, let's have a public interface Use functional interface that can be applied to Items (along
 * with other
 * functional interfaces).  This way we can add the actual functionality at run time.
 * NOTE
 */
public interface Item extends Entity {

    /**
     * User the item
     *
     * @param player
     * @param adventure
     * @return
     */
    List<Response> use(Player player, Adventure adventure, EntityService entityService);

    /**
     * Determine if a given target entity is appropriate
     *
     * @param entity
     * @return
     */
    boolean hasTarget(Entity entity);

    /**
     * Number of hands the item takes to use
     *
     * @return
     */
    int hands();

    /**
     * Encumbrance value of the item when carried.
     *
     * @return
     */
    int encumbrance();

    /**
     * Behavior when the item is misused, possibly on a specific feature
     *
     * @param feature
     * @param player
     * @param adventure
     * @return
     */
    List<Response> misUse(Feature feature, Player player, Adventure adventure, EntityService entityService);
}
