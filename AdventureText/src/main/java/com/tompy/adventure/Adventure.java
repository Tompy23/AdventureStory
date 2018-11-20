package com.tompy.adventure;

import com.tompy.common.Clock;
import com.tompy.directive.Direction;
import com.tompy.entity.Actor.MoveStrategyFactory;
import com.tompy.entity.EntityService;
import com.tompy.io.UserIO;
import com.tompy.persistence.AdventureData;
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

    void load(AdventureData data);

    AdventureData save();

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
