package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

/**
 * The part of an event where something happens
 */
public interface Action {

    /**
     * Apply the action for the event
     *
     * @param player
     * @param adventure
     * @return
     */
    List<Response> apply(Player player, Adventure adventure);
}
