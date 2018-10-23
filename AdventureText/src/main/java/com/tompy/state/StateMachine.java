package com.tompy.state;

/**
 * State functions
 */
public interface StateMachine {

    /**
     * Change to a new state
     *
     * @param newState
     */
    void changeState(AdventureState newState);
}
