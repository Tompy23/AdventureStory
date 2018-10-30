package com.tompy.io;

import com.tompy.command.Command;
import com.tompy.command.CommandFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

/**
 * User input facility
 */
public interface UserIO {

    /**
     *
     * @param inStream
     * @param outStream
     * @param commandFactory
     */
    void init(InputStream inStream, PrintStream outStream, CommandFactory commandFactory);

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

    /**
     *
     * @param text
     */
    void print(String text);

    /**
     *
     * @param text
     */
    void println(String text);

    /**
     *
     */
    void println();
}
