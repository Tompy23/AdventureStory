package com.tompy.execution;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureFactory;
import com.tompy.attribute.AttributeManagerFactoryImpl;
import com.tompy.command.CommandFactoryImpl;
import com.tompy.directive.Direction;
import com.tompy.entity.EntityService;
import com.tompy.entity.EntityServiceImpl;
import com.tompy.entity.event.EventManagerFactoryImpl;
import com.tompy.exit.ExitBuilderFactoryImpl;
import com.tompy.io.UserIO;
import com.tompy.io.UserIOImpl;
import com.tompy.map.AdventureMapBuilderFactoryImpl;
import com.tompy.persistence.AdventureData;
import com.tompy.player.Player;
import com.tompy.player.PlayerImpl;
import com.tompy.state.AdventureState;
import com.tompy.state.AdventureStateFactory;
import com.tompy.state.AdventureStateFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Execution {
    private static final Logger LOGGER = LogManager.getLogger(Execution.class);
    private static final int CREATE_NEW_GAME = 1;
    private static final int LOAD_EXISTING_GAME = 2;
    private static final int END_GAME = 1;
    private static final int SAVE_GAME = 2;
    private final EntityServiceImpl.EntityServiceBuilder entityServiceBuilder;
    private final UserIO ui;
    private EntityService entityService;
    private AdventureStateFactory stateFactory;
    private Adventure adventure;
    private Player player;
    private AdventureState adventureState;
    private String startRoom;
    private Direction entryDirection;
    private List<Adventure> adventureList = new ArrayList<>();

    public Execution() {
        entityServiceBuilder =
                EntityServiceImpl.createBuilder(new AttributeManagerFactoryImpl(), new EventManagerFactoryImpl());
        ui = new UserIOImpl();
        ui.init(System.in, System.out, null);
    }

    public int execute(AdventureFactory adventureFactory) {
        int startValue = 0;
        switch (start().intValue()) {
            case CREATE_NEW_GAME:
                LOGGER.info("Starting new game");
                create(adventureFactory);
                break;
            case LOAD_EXISTING_GAME:
                LOGGER.info("Loading existing game");
                startValue = load(adventureFactory);
                break;
        }

        if (startValue == 0) {
            LOGGER.info("Player [{}] enters the adventure", player.getName());

            ui.println(String.format("%s, you are about to enter a world of adventure... ", player.getName()));

            adventure.start(adventureState, startRoom, entryDirection, entityService);

            ui.println(String.format("%s has left the adventure.", player.getName()));

            switch (end().intValue()) {
                case END_GAME:
                    LOGGER.info("Ending game");
                    return 0;
                case SAVE_GAME:
                    LOGGER.info("Saving game");
                    return save();
            }
        }

        return startValue;
    }

    private Long start() {
        Map<Long, String> startOptions = new HashMap<>();
        startOptions.put(Integer.toUnsignedLong(CREATE_NEW_GAME), "Create new game");
        startOptions.put(Integer.toUnsignedLong(LOAD_EXISTING_GAME), "Load existing game");
        return ui.getSelection(startOptions);
    }

    private Long end() {
        Map<Long, String> startOptions = new HashMap<>();
        startOptions.put(Integer.toUnsignedLong(END_GAME), "Quit");
        startOptions.put(Integer.toUnsignedLong(SAVE_GAME), "Save game");
        return ui.getSelection(startOptions);
    }

    private void create(AdventureFactory adventureFactory) {
        entityService = entityServiceBuilder.build();

        ui.init(System.in, System.out, new CommandFactoryImpl());

        player = new PlayerImpl(ui.getResponse("Player name?"), null);
        ui.println();

        adventure = adventureFactory.create();
        adventure.init(player, entityService, new ExitBuilderFactoryImpl(), new AdventureMapBuilderFactoryImpl(), ui,
                adventure.getPropertiesFilename());

        stateFactory = new AdventureStateFactoryImpl(player, adventure, entityService);

        adventure.create();

        startRoom = adventure.getStartRoomName();
        entryDirection = adventure.getEntryDirection();
        adventureState = stateFactory.getExploreState();

    }

    private int load(AdventureFactory adventureFactory) {
        String filename = ui.getResponse("File Name?");


        if (!filename.isEmpty()) {
            filename = filename + ".adv";
            try {
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                AdventureData adventureData = (AdventureData) ois.readObject();

                entityService =
                        entityServiceBuilder.entityKey(adventureData.getEntityKey()).actors(adventureData.getActors())
                                .areas(adventureData.getAreas()).attributeManagers(adventureData.getAttributeManagers())
                                .encounters(adventureData.getEncounters()).entityMap(adventureData.getEntityMap())
                                .eventManagers(adventureData.getEventManagers()).events(adventureData.getEvents())
                                .features(adventureData.getFeatures()).items(adventureData.getItems()).build();

                player = adventureData.getPlayer();
                adventure = adventureFactory.create();
                adventure
                        .init(player, entityService, new ExitBuilderFactoryImpl(), new AdventureMapBuilderFactoryImpl(),
                                ui, adventure.getPropertiesFilename());

                stateFactory = new AdventureStateFactoryImpl(player, adventure, entityService);
                ui.init(System.in, System.out, new CommandFactoryImpl());
                ois.close();
                fis.close();

                startRoom = player.getArea().getName();
                // TODO Do we want to set this back to Explore?  Or will the Loader pull it in correctly?
                adventureState = stateFactory.getExploreState();
                entryDirection = null;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return 6;
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
                return 7;
            }
        }
        return 0;

    }

    public int save() {
        String filename = ui.getResponse("Save?");
        if (!filename.isEmpty()) {
            filename = filename + ".adv";
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(adventure.save());
                oos.close();
                fos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return 8;
            }
        }
        return 0;
    }
}
