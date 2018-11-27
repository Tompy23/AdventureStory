package com.tompy.robots;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureFactory;
import com.tompy.adventure.AdventureImpl;
import com.tompy.adventure.AdventureUtils;
import com.tompy.common.Coordinates2DImpl;
import com.tompy.directive.Direction;
import com.tompy.directive.MoveStrategyType;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.area.Area;
import com.tompy.exit.Exit;
import com.tompy.map.AdventureMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.NoRouteToHostException;

import static com.tompy.directive.Direction.*;

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
        return "barn";
    }

    @Override
    public Direction getEntryDirection() {
        return null;
    }

    @Override
    public String getPropertiesFilename() {
        return "i18n.properties";
    }

    @Override
    public void create() {
        AdventureMap mapCompound = buildAdventureMap(AdventureUtils.getMap("compound.map", messages), 20, 50);
        addMap("compound", mapCompound);
        mapCompound.addLegend('.', "clear");
        mapCompound.addLegend('|', "woods");
        mapCompound.addLegend('*', "path");
        mapCompound.addLegend('W', "warehouse");
        mapCompound.addLegend('B', "barracks");
        mapCompound.addLegend('C', "commons");
        mapCompound.addLegend('~', "river");

        Area barn = buildArea("barn", new Coordinates2DImpl(8, 19));
        Area field1 = buildArea("west_field", new Coordinates2DImpl(20, 17));
        Area field2 = buildArea("east_field", new Coordinates2DImpl(43, 15));
        Area southPath = buildArea("south_path", new Coordinates2DImpl(24, 14));
        Area northPath = buildArea("north_path", new Coordinates2DImpl(24, 7));
        Area center = buildArea("center", new Coordinates2DImpl(33, 8));
        Area westCommon = buildArea("west_common", new Coordinates2DImpl(31, 8));
        Area eastCommon = buildArea("east_common", new Coordinates2DImpl(38, 8));
        Area barracks = buildArea("barracks", new Coordinates2DImpl(33, 6));
        Area warehouse = buildArea("warehouse", new Coordinates2DImpl(33, 10));
        Area river = buildArea("river", new Coordinates2DImpl(20, 6));
        Area forest = buildArea("forest", new Coordinates2DImpl(10, 10));

        Exit exitBarnField1 = buildExit(barn, DIRECTION_EAST, field1, DIRECTION_WEST, true, 30);
        Exit exitField2Path = buildExit(field2, DIRECTION_WEST, southPath, DIRECTION_EAST, true, 30);
        Exit exitField1Path = buildExit(field1, DIRECTION_EAST, southPath, DIRECTION_WEST, true, 30);
        Exit exitBarnForest = buildExit(barn, DIRECTION_NORTH, forest, DIRECTION_SOUTH, true, 60);
        Exit exitSouthNorth = buildExit(southPath, DIRECTION_NORTH, northPath, DIRECTION_SOUTH, true, 45);
        Exit exitPathForest = buildExit(northPath, DIRECTION_WEST, forest, DIRECTION_EAST, true, 30);
        Exit exitField2Warehouse = buildExit(field2, DIRECTION_NORTH, warehouse, DIRECTION_SOUTH, true, 30);
        Exit exitWarehouseCenter = buildExit(warehouse, DIRECTION_NORTH, center, DIRECTION_SOUTH, true, 10);
        Exit exitBarracksCenter = buildExit(barracks, DIRECTION_SOUTH, center, DIRECTION_NORTH, true, 10);
        Exit exitWestCommonCenter = buildExit(westCommon, DIRECTION_EAST, center, DIRECTION_WEST, true, 10);
        Exit exitEastCommonCenter = buildExit(eastCommon, DIRECTION_WEST, center, DIRECTION_EAST, true, 10);
        Exit exitPathCenter = buildExit(northPath, DIRECTION_WEST, center, DIRECTION_EAST, true, 30);



        Actor actorPhil = buildActor("Phil", "short stature with dark curly hair", MoveStrategyType.MOVE_FOLLOW);
        barn.addActor(actorPhil);
        Actor actorJack = buildActor("Jack", "tall and thin with short dark hair", MoveStrategyType.MOVE_FOLLOW);
        barn.addActor(actorJack);

    }
}
