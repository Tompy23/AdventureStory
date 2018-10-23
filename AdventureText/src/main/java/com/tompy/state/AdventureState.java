package com.tompy.state;

/**
 * The state of an adventure for processing in different ways
 */
public interface AdventureState {

    /**
     * Before the new state is processed for the first time
     */
    void start();

    /**
     * The process which will be called repeatedly until the state is not current
     */
    void process();

    /**
     * After the final process of the state
     */
    void end();
}
