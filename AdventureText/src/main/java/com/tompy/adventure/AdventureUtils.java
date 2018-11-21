package com.tompy.adventure;

import com.tompy.attribute.Attribute;
import com.tompy.directive.CommandType;
import com.tompy.directive.Direction;
import com.tompy.directive.EventType;
import com.tompy.map.AdventureMap;
import com.tompy.messages.MessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static com.tompy.directive.Direction.*;
import static com.tompy.directive.EventType.*;

/**
 * Util class for handling Directions, EventTypes and Attributes
 */
public class AdventureUtils {
    private static Map<String, Direction> directionMap = new HashMap<>();
    private static Map<String, CommandType> commandTypeMap = new HashMap<>();
    private static Map<String, Attribute> attributeMap = new HashMap<>();
    private static Random r = new Random();

    static {
        for (Direction d : Direction.values()) {
            directionMap.put(d.getDescription().toUpperCase(), d);
        }
        for (CommandType ct : CommandType.values()) {
            commandTypeMap.put(ct.getDescription().toUpperCase(), ct);
        }
        for (Attribute a : Attribute.values()) {
            attributeMap.put(a.getName().toUpperCase(), a);
        }
    }

    /**
     * Validates the String can be mapped to a Direction
     *
     * @param dir
     * @return
     */
    public static boolean isDirection(String dir) {
        return (directionMap.containsKey(dir.toUpperCase()));
    }

    /**
     * Converts a String to a Direction
     *
     * @param dir
     * @return
     */
    public static Direction getDirection(String dir) {
        return directionMap.get(dir.toUpperCase());
    }

    /**
     * Converts a String to a CommandType
     *
     * @param type
     * @return
     */
    public static CommandType getCommandType(String type) {
        return commandTypeMap.get(type.toUpperCase());
    }

    /**
     * Converts a String to an Attribute
     *
     * @param name
     * @return
     */
    public static Attribute getAttribute(String name) {
        return attributeMap.get(name.toUpperCase());
    }

    /**
     * Returns opposite direction
     *
     * @param direction
     * @return
     */
    public static Direction getOppositeDirection(Direction direction) {
        Direction returnValue = null;

        switch (direction) {
            case DIRECTION_NORTH:
                returnValue = DIRECTION_SOUTH;
                break;
            case DIRECTION_EAST:
                returnValue = DIRECTION_WEST;
                break;
            case DIRECTION_SOUTH:
                returnValue = DIRECTION_NORTH;
                break;
            case DIRECTION_WEST:
                returnValue = DIRECTION_EAST;
                break;
        }

        return returnValue;
    }

    public static Direction getRandomDirection() {
        switch(r.nextInt(4)){
            case 0:
                return DIRECTION_NORTH;
            case 1:
                return DIRECTION_EAST;
            case 2:
                return DIRECTION_SOUTH;
            case 3:
                return DIRECTION_WEST;
        }
        return null;
    }

    /**
     * Converts a direction into an Area Search EventType
     *
     * @param direction
     * @return
     */
    public static EventType getAreaSearchEventType(Direction direction) {
        switch (direction) {
            case DIRECTION_NORTH:
                return EVENT_AREA_NORTH_SEARCH;
            case DIRECTION_EAST:
                return EVENT_AREA_EAST_SEARCH;
            case DIRECTION_SOUTH:
                return EVENT_AREA_SOUTH_SEARCH;
            case DIRECTION_WEST:
                return EVENT_AREA_WEST_SEARCH;
            default:
                return null;
        }
    }

    /**
     * Converts a Direction to an Area Exit EventType
     *
     * @param direction
     * @return
     */
    public static EventType getAreaExitEventType(Direction direction) {
        switch (direction) {
            case DIRECTION_NORTH:
                return EVENT_AREA_EXIT_NORTH;
            case DIRECTION_EAST:
                return EVENT_AREA_EXIT_EAST;
            case DIRECTION_SOUTH:
                return EVENT_AREA_EXIT_SOUTH;
            case DIRECTION_WEST:
                return EVENT_AREA_EXIT_WEST;
            default:
                return null;
        }
    }

    /**
     * Converts a Direction to an Area Enter EventType
     *
     * @param direction
     * @return
     */
    public static EventType getAreaEnterEventType(Direction direction) {
        switch (direction) {
            case DIRECTION_NORTH:
                return EVENT_AREA_ENTER_NORTH;
            case DIRECTION_EAST:
                return EVENT_AREA_ENTER_EAST;
            case DIRECTION_SOUTH:
                return EVENT_AREA_ENTER_SOUTH;
            case DIRECTION_WEST:
                return EVENT_AREA_ENTER_WEST;
            default:
                return EVENT_AREA_ENTER;
        }
    }

    /**
     * Parses a command into its component parts
     *
     * @param command
     * @param splits
     * @return
     */
    public static String[] parseCommand(String[] command, List<String> splits) {
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        int split = command.length + 1;

        splits = splits.stream().map(String::toUpperCase).collect(Collectors.toList());

        for (int i = 0; i < command.length; i++) {
            if (splits.contains(command[i].toUpperCase())) {
                split = i;
                break;
            }
        }

        String[] returnValue = new String[split < command.length + 1 ? 2 : 1];

        for (int i = 1; i < command.length; i++) {
            if (i < split) {
                first.append(command[i] + " ");
            } else if (i > split) {
                second.append(command[i] + " ");
            }
        }
        returnValue[0] = first.toString().trim();
        if (returnValue.length > 1) {
            returnValue[1] = second.toString().trim();
        }

        return returnValue;
    }

    /**
     * Retrieve a premade map from resources
     *
     * @param name - key of the resource
     * @param messages - Message handler containing the resource
     * @return - char[] containing the map data
     */
    public static char[] getMap(String name, MessageHandler messages) {
        StringBuilder sb = new StringBuilder();
        String property;
        int i = 1;
        do {
            property = messages.get(String.format("%s.%d", name, i++));
            if (property != null) {
                sb.append(property);
            }
        } while (property != null);

        return sb.toString().toCharArray();
    }
}
