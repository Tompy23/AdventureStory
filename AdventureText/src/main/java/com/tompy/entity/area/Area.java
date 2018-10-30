package com.tompy.entity.area;

import com.tompy.adventure.Adventure;
import com.tompy.command.Command;
import com.tompy.directive.Direction;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.EntityService;
import com.tompy.entity.compartment.Compartment;
import com.tompy.entity.feature.Feature;
import com.tompy.exit.Exit;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

/**
 * Represents a point of movement connected by an {@link Exit ).  A {@link Player} is always in a single Area
 */
public interface Area extends Compartment {

    /**
     * Add an exit to the Area
     *
     * @param direction - the {@link Direction} where the exit is located
     * @param exit      - the {@link Exit} to add
     */
    void installExit(Direction direction, Exit exit);

    /**
     * Add a {@link Feature} to the Area.
     *
     * @param feature   - The feature to add
     * @param direction - An optional direction within the area [DO NOT USE]
     */
    void installFeature(Feature feature, Direction direction);

    /**
     * Name of the Area to identify it
     *
     * @return - The name of the area
     */
    String getName();

    /**
     * Retrieve a specific {@link Exit}
     *
     * @param direction - The {@link Direction} being requested
     * @return - The {@link Exit}
     */
    Exit getExitForDirection(Direction direction);

    /**
     * The action which happens when an Area is entered by the {@link Player}
     *
     * @param direction - The {@link Direction} the {@link Player} enters
     *             * @param player    - The {@link Player} info
     * @param adventure - The {@link Adventure} info
     * @return - A list of {@link Response} to display
     */
    List<Response> enter(Direction direction, Player player, Adventure adventure, EntityService entityService);

    /**
     * The action which happens when a {@link Player} leaves an Area
     *
     * @param direction - The {@link Direction} the {@link Player} leaves
     * @param player    - The {@link Player} info
     * @param adventure - The {@link Adventure} info
     * @return - A list of {@link Response} to display
     */
    List<Response> exit(Direction direction, Player player, Adventure adventure, EntityService entityService);

    /**
     * The action which happens when a {@link Player} uses the Search {@link Command}
     *
     * @param player    - The {@link Player} info
     * @param adventure - The {@link Adventure} info
     * @return - A list of {@link Response} to display
     */
    List<Response> search(Player player, Adventure adventure, EntityService entityService);

    /**
     * The action which happens when a {@link Player} searches in a specific {@link Direction}
     *
     * @param direction - The {@link Direction} searched
     * @param player    - The {@link Player} info
     * @param adventure - The {@link Adventure} info
     * @return - A list of {@link Response} to display
     */
    List<Response> searchDirection(Direction direction, Player player, Adventure adventure, EntityService entityService);

    /**
     * Return all features in the area
     *
     * @return - A list of {@link Feature} in room
     */
    List<Feature> getAllFeatures();

    /**
     * Add a Actor to an area when he enters
     *
     * @param actor
     */
    void addActor(Actor actor);

    /**
     * Remove a Actor from an area when he exits
     *
     * @param actor
     */
    void removeActor(Actor actor);

    /**
     * Return if there is a Actor in this area
     *
     * @param actor
     * @return
     */
    boolean isActor(Actor actor);

    /**
     * Get a list of all characters in area
     *
     * @return
     */
    List<Actor> getAllActors();
}
