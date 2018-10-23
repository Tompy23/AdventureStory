package com.tompy.io;

import com.tompy.command.Command;

import java.util.Map;

/**
 * User input facility
 */
public interface UserInput {

    /**
     * User enters text and produces a command
     *
     * @return
     */
    Command getCommand();

    /**
     * User is given a list of options and chooses one.
     *
     * @param selection
     * @return
     */
    Long getSelection(Map<Long, String> selection);

    /**
     * User inputs a raw string and it is returned
     * @param question
     * @return
     */
    String getResponse(String question);

    /**
     * ???
     */
    void quit();
}
