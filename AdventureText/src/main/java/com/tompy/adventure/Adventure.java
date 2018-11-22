package com.tompy.adventure;

import com.tompy.common.Clock;
import com.tompy.directive.Direction;
import com.tompy.entity.Actor.MoveStrategyFactory;
import com.tompy.entity.EntityService;
import com.tompy.exit.ExitBuilderFactory;
import com.tompy.io.UserIO;
import com.tompy.map.AdventureMap;
import com.tompy.map.AdventureMapBuilderFactory;
import com.tompy.persistence.AdventureData;
import com.tompy.player.Player;
import com.tompy.state.AdventureState;
import com.tompy.state.AdventureStateFactory;
import com.tompy.state.StateMachine;

import java.io.PrintStream;
import java.io.Serializable;

/**
 * Defines functions which interact with the state of the adventure.
 */
public interface Adventure extends StateMachine, Clock, Serializable {

    /**
     * Create the adventure elements
     */
    void create();

    /**
     *
     * @param data
     */
    void load(AdventureData data);

    /**
     *
     * @return
     */
    AdventureData save();

    /**
     *
     */
    void init(Player player, EntityService entityService, ExitBuilderFactory exitBuilderFactory,
            AdventureMapBuilderFactory mapBuilderFactory, UserIO userInput, String propertiesFilename);

    /**
     *
     * @return
     */
    String getName();

    /**
     *
     * @return
     */
    String getStartRoomName();

    /**
     *
     * @return
     */
    Direction getEntryDirection();

    /**
     *
     * @return
     */
    String getPropertiesFilename();

    /**
     * Set the state factory for the adventure
     *
     * @param stateFactory - The state factory
     */
    void setStateFactory(AdventureStateFactory stateFactory);

    /**
     * Expose the user input
     *
     * @return - The user input implementation
     */
    UserIO getUI();

    /**
     *
     * @param key
     * @param map
     */
    void addMap(String key, AdventureMap map);

    /**
     *
     * @param key
     * @return
     */
    AdventureMap getMap(String key);


    /**
     * Begin an adventure for a player
     *
     * @param state - The starting state for the adventure
     */
    void start(AdventureState state, String startRoom, Direction direction, EntityService entityService);

    /**
     * Gracefully stop an adventure for a specific player
     */
    void stop();
}
