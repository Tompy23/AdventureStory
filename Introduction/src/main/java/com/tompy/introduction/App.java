package com.tompy.introduction;


import com.tompy.adventure.Adventure;
import com.tompy.attribute.AttributeManagerFactoryImpl;
import com.tompy.command.CommandFactoryImpl;
import com.tompy.entity.EntityService;
import com.tompy.entity.EntityServiceImpl;
import com.tompy.entity.event.EventManagerFactoryImpl;
import com.tompy.exit.ExitBuilderFactoryImpl;
import com.tompy.io.UserIO;
import com.tompy.io.UserIOImpl;
import com.tompy.persistence.AdventureData;
import com.tompy.player.Player;
import com.tompy.player.PlayerImpl;
import com.tompy.state.AdventureStateFactory;
import com.tompy.state.AdventureStateFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

import static com.tompy.directive.Direction.DIRECTION_SOUTH;

/**
 * Hello world!
 */
public class App {
    private final static Logger LOGGER = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        App a = new App();
        System.exit(a.go(args));
    }

    public int go(String[] args) {
        EntityServiceImpl.EntityServiceBuilder entityServiceBuilder =
                EntityServiceImpl.createBuilder(new AttributeManagerFactoryImpl(), new EventManagerFactoryImpl());
        UserIO ui = new UserIOImpl();
        ui.init(System.in, System.out, null);

        EntityService entityService = null;

        // TODO this is where we start...

        String filename = ui.getResponse("File Name?");

        AdventureStateFactory stateFactory;
        Adventure adventure;
        Player player;
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
                adventure = new Introduction(player, entityService, new ExitBuilderFactoryImpl(), ui);

                stateFactory = new AdventureStateFactoryImpl(player, adventure, entityService);
                ui.init(System.in, System.out, new CommandFactoryImpl());
                ois.close();
                fis.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return 6;
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
                return 7;
            }
        } else {
            entityService = entityServiceBuilder.build();

            ui.init(System.in, System.out, new CommandFactoryImpl());

            player = new PlayerImpl(ui.getResponse("Player name?"), null);
            ui.println();

            adventure = new Introduction(player, entityService, new ExitBuilderFactoryImpl(), ui);

            stateFactory = new AdventureStateFactoryImpl(player, adventure, entityService);

            adventure.create();
        }

        LOGGER.info("Player [{}] enters the adventure", player.getName());

        ui.println(String.format("%s, you are about to enter a world of adventure... ", player.getName()));
        ui.println("Your quest is to defeat a nasty orc to the north.");
        ui.println();


        adventure.start(stateFactory.getExploreState(), "StartRoom", DIRECTION_SOUTH, entityService);

        ui.println(String.format("%s has left the adventure.", player.getName()));

        // TODO THIS IS WHERE WE SAVE...
        filename = ui.getResponse("Save?");
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
