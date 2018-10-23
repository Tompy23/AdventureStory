package com.tompy.state;

/**
 * State functions
 */
public interface StateMachine {

    /**
     * Process the current state
     */
    void process();

    /**
     * Change to a new state
     *
     * @param newState
     */
    void changeState(AdventureState newState);
}
