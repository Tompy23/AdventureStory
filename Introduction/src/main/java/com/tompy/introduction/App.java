package com.tompy.introduction;


import com.tompy.adventure.Adventure;
import com.tompy.attribute.AttributeManagerFactoryImpl;
import com.tompy.entity.EntityService;
import com.tompy.entity.EntityServiceImpl;
import com.tompy.entity.event.EventManagerFactoryImpl;
import com.tompy.exit.ExitBuilderFactoryImpl;
import com.tompy.io.UserInput;
import com.tompy.io.UserInputTextImpl;
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
        InputStream inStream = System.in;
        PrintStream outStream = System.out;
        EntityService entityService =
                new EntityServiceImpl(new AttributeManagerFactoryImpl(), new EventManagerFactoryImpl());
        UserInput ui = new UserInputTextImpl(inStream, outStream, entityService);
        Player player = new PlayerImpl(ui.getResponse("Player name?"), null);
        outStream.println();

        String filename = ui.getResponse("File Name?");

        AdventureStateFactory stateFactory;
        Adventure adventure;
        if (!filename.isEmpty()) {
            filename = filename + ".adv";
            try {
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                adventure = (Adventure) ois.readObject();
                stateFactory = new AdventureStateFactoryImpl(player, adventure, ui, outStream, entityService);
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
            adventure = new Introduction(player, entityService, new ExitBuilderFactoryImpl(), ui, outStream);

            stateFactory = new AdventureStateFactoryImpl(player, adventure, ui, outStream, entityService);

            adventure.create();
        }

        LOGGER.info("Player [{}] enters the adventure", player.getName());

        outStream.println(String.format("%s, you are about to enter a world of adventure... ", player.getName()));
        outStream.println("Your quest is to defeat a nasty orc to the north.");
        outStream.println();


        adventure.start(stateFactory.getExploreState(), "StartRoom", DIRECTION_SOUTH, entityService);

        outStream.println(String.format("%s has left the adventure.", player.getName()));

        filename = ui.getResponse("Save?");
        if (!filename.isEmpty()) {
            filename = filename + ".adv";
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(adventure);
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
