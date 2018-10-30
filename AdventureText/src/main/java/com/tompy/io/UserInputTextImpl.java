package com.tompy.io;

import com.tompy.command.CommandFactory;
import com.tompy.command.CommandFactoryImpl;
import com.tompy.command.Command;
import com.tompy.entity.EntityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Map;
import java.util.Objects;

public class UserInputTextImpl implements UserInput, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(UserInputTextImpl.class);
    private CommandFactory factory;
    private transient BufferedReader br = null;
    private transient PrintStream outStream;
    private transient InputStream inStream;

    public UserInputTextImpl(InputStream inStream, PrintStream outStream, EntityService entityService) {
        this.inStream = Objects.requireNonNull(inStream, "In Stream cannot be null.");
        this.outStream = Objects.requireNonNull(outStream, "Out Stream cannot be null.");
        br = new BufferedReader(new InputStreamReader(inStream));
        factory = new CommandFactoryImpl(entityService);
    }

    @Override
    public Command getCommand() {
        Command returnValue = null;
        try {
            outStream.print(">>> ");
            String input = br.readLine();
            LOGGER.info("User Command input [{}]", input);
            returnValue = factory.createCommand(input.split(" "));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return returnValue;
    }

    @Override
    public Long getSelection(Map<Long, String> options) {
        Long[] selectionList = setUpSelection(options);
        displaySelection(options, selectionList);
        try {
            outStream.print("=== ");
            String input = br.readLine();
            LOGGER.info("User Choice input [{}]", input);
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > options.size()) {
                // TODO this is a bad choice.  Is this ok?
                outStream.println("Not a valid choice.  Try again.");
                return getSelection(options);
            } else {
                return selectionList[choice - 1];
            }
        } catch (IOException | NumberFormatException e) {
            outStream.println("Not a valid choice.  Try again.");
            return getSelection(options);
        }
    }

    @Override
    public String getResponse(String question) {
        String returnValue = "";
        try {
            outStream.println(question);
            outStream.print("??? ");
            returnValue = br.readLine();
        } catch (IOException ioe) {
            outStream.println("ERROR");
            ioe.printStackTrace();
        }
        LOGGER.info("User Responded with [{}]", returnValue);
        return returnValue;
    }

    @Override
    public void quit() {
        try {
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private Long[] setUpSelection(Map<Long, String> options) {
        Long[] returnValue = new Long[options.keySet().size()];
        Integer index = 0;
        for (Long key : options.keySet()) {
            returnValue[index++] = key;
        }

        return returnValue;
    }

    private void displaySelection(Map<Long, String> options, Long[] selections) {
        Integer index = 1;
        for (Long selection : selections) {
            outStream.println(index++ + ". " + options.get(selection));
        }
    }
}
