package com.tompy.robots;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureImpl;
import com.tompy.entity.EntityService;
import com.tompy.exit.ExitBuilderFactory;
import com.tompy.io.UserIO;
import com.tompy.map.AdventureMapBuilderFactory;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Robots extends AdventureImpl implements Adventure {
    private static final Logger LOGGER = LogManager.getLogger(Robots.class);

    public Robots() {
    }

    public Robots(Player player, EntityService entityService, ExitBuilderFactory exitBuilderFactory,
            AdventureMapBuilderFactory mapBuilderFactory, UserIO userInput) {
        super(player, entityService, exitBuilderFactory, mapBuilderFactory, userInput);
    }

    @Override
    public void create() {
    }
}
