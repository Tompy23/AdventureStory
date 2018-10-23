package com.tompy.entity.event;

import com.tompy.adventure.Adventure;
import com.tompy.entity.Entity;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriggerOnceAfterDelay extends TriggerImpl {
    public static final Logger LOGGER = LogManager.getLogger(TriggerOnceAfterDelay.class);
    private final int delay;
    private int counter = 0;

    public TriggerOnceAfterDelay(Entity entity, int delay) {
        super(entity);
        this.delay = delay;
    }

    @Override
    public boolean pull(Player player, Adventure adventure) {
        LOGGER.info("Checking trigger.");
        boolean returnValue = false;
        if (counter == delay) {
            returnValue = true;
        }
        counter++;
        return returnValue;
    }
}
