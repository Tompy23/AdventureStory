package com.tompy.state;

import com.tompy.entity.EntityService;

/**
 * State functions
 */
public interface StateMachine {

    /**
     * Change to a new state
     *
     * @param newState
     */
    void changeState(AdventureState newState, EntityService entityService);
}
