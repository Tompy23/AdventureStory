package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;

/**
 * The trigger mechanism for an Event
 */
public interface Trigger {

    /**
     * Determines if an Action should be aplied
     *
     * @param player
     * @param adventure
     * @param entityService
     * @return
     */
    boolean pull(Player player, Adventure adventure, EntityService entityService);
}
