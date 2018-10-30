package com.tompy.state;

import com.tompy.entity.EntityService;
import com.tompy.io.UserIO;

import java.io.PrintStream;

/**
 * The state of an adventure for processing in different ways
 */
public interface AdventureState {

    /**
     * Before the new state is processed for the first time
     */
    void start(EntityService entityService, UserIO io);

    /**
     * The process which will be called repeatedly until the state is not current
     */
    void process(EntityService entityService, UserIO io);

    /**
     * After the final process of the state
     */
    void end(EntityService entityService, UserIO io);
}
