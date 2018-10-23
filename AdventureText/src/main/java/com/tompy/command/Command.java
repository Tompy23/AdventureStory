package com.tompy.command;

import com.tompy.adventure.Adventure;
import com.tompy.directive.CommandType;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

/**
 * A Command represents the action taken
 */
public interface Command {

    /**
     * Retrieve the type of command
     *
     * @return - The {@link CommandType} of this command
     */
    CommandType getType();

    /**
     * This executes the command.  A specific Command implementation will contain more information about the command.
     *
     * @param player    - The {@link Player} information available to this command
     * @param adventure - The {@link Adventure} information available to this command
     * @return - A list of {@link Response}
     */
    List<Response> execute(Player player, Adventure adventure);
}
