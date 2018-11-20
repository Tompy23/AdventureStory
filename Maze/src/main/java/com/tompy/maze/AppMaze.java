package com.tompy.maze;


import com.tompy.adventure.Adventure;
import com.tompy.attribute.AttributeManagerFactoryImpl;
import com.tompy.command.CommandFactoryImpl;
import com.tompy.entity.EntityService;
import com.tompy.entity.EntityServiceImpl;
import com.tompy.entity.event.EventManagerFactoryImpl;
import com.tompy.exit.ExitBuilderFactoryImpl;
import com.tompy.io.UserIO;
import com.tompy.io.UserIOImpl;
import com.tompy.map.AdventureMapBuilderFactoryImpl;
import com.tompy.player.Player;
import com.tompy.player.PlayerImpl;
import com.tompy.state.AdventureStateFactory;
import com.tompy.state.AdventureStateFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.PrintStream;

import static com.tompy.directive.Direction.DIRECTION_NORTH;

/**
 * Hello world!
 */
public class AppMaze {
    private final static Logger LOGGER = LogManager.getLogger(AppMaze.class);

    public static void main(String[] args) {
        AppMaze a = new AppMaze();
        System.exit(a.go(args));
    }

    public int go(String[] args) {
        InputStream inStream = System.in;
        PrintStream outStream = System.out;
        EntityService entityService =
                EntityServiceImpl.createBuilder(new AttributeManagerFactoryImpl(), new EventManagerFactoryImpl())
                        .build();
        UserIO ui = new UserIOImpl();
        ui.init(inStream, outStream, new CommandFactoryImpl());
        Player player = new PlayerImpl(ui.getResponse("Player name?"), null);
        outStream.println();
        Adventure adventure =
                new Maze(player, entityService, new ExitBuilderFactoryImpl(), new AdventureMapBuilderFactoryImpl(), ui);

        AdventureStateFactory stateFactory = new AdventureStateFactoryImpl(player, adventure, entityService);

        LOGGER.info("Player [{}] enters the adventure", player.getName());

        outStream.println(String.format("%s, you are about to enter a world of adventure... ", player.getName()));
        outStream.println();

        adventure.create();

        adventure.start(stateFactory.getExploreState(), "Room-0", DIRECTION_NORTH, entityService);

        outStream.println(String.format("%s has left the adventure.", player.getName()));

        return 0;
    }
}
