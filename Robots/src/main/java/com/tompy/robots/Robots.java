package com.tompy.robots;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureFactory;
import com.tompy.adventure.AdventureImpl;
import com.tompy.directive.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.tompy.directive.Direction.DIRECTION_SOUTH;

public class Robots extends AdventureImpl implements Adventure {
    private static final Logger LOGGER = LogManager.getLogger(Robots.class);

    public static AdventureFactory getFactory() {
        return Robots::createAdventure;
    }

    public static Adventure createAdventure() {
        return new Robots();
    }

    @Override
    public String getName() {
        return "Robots";
    }

    @Override
    public String getStartRoomName() {
        return "";
    }

    @Override
    public Direction getEntryDirection() {
        return DIRECTION_SOUTH;
    }

    @Override
    public String getPropertiesFilename() {
        return "i18n.properties";
    }


    @Override
    public void create() {
    }
}
