package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.entity.EntityService;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriggerAlwaysAfterDelay extends TriggerImpl {
    public static final Logger LOGGER = LogManager.getLogger(TriggerAlwaysAfterDelay.class);
    private final int delay;
    private int counter = 0;

    public TriggerAlwaysAfterDelay(Entity entity, int delay) {
        super(entity);
        this.delay = delay;
    }

    @Override
    public boolean pull(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info("Checking trigger.");
        if (counter >= delay) {
            return true;
        }
        counter++;
        return false;
    }
}
