package com.tompy.entity.encounter;

import com.tompy.adventure.Adventure;
import com.tompy.directive.EncounterType;
import com.tompy.entity.EntityBuilderHelperImpl;
import com.tompy.entity.EntityImpl;
import com.tompy.entity.EntityService;
import com.tompy.entity.event.Event;
import com.tompy.entity.item.Item;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.tompy.directive.EventType.EVENT_INTERACTION;


public class EncounterImpl extends EntityImpl implements Encounter {
    public static final Logger LOGGER = LogManager.getLogger(EncounterImpl.class);
    protected final Player player;
    protected final Adventure adventure;

    public EncounterImpl(Long key, String name, List<String> descriptors, String description, Player player,
            Adventure adventure) {
        super(key, name, descriptors, description);
        this.player = player;
        this.adventure = adventure;
    }

    public static EncounterBuilder createBuilder(Long key, EntityService entityService, Player player,
            Adventure adventure) {
        return new EncounterBuilderImpl(key, entityService, player, adventure);
    }


    @Override
    public Map<Long, String> getOptions(EntityService entityService) {
        LOGGER.info("Retrieving options for encounter.");
        Map<Long, String> returnValue = new HashMap<>();
        for (Event event : entityService.get(this, EVENT_INTERACTION)) {
            if (event.pull(player, adventure, entityService)) {
                returnValue.put(event.getKey(), event.getDescription());
            }
        }
        return returnValue;
    }

    @Override
    public List<Response> act(Long option, EntityService entityService) {
        LOGGER.info("Acting on chosen option for encounter.");
        for (Event event : entityService.get(this, EVENT_INTERACTION)) {
            if (event.getKey() == option) {
                return event.apply(player, adventure, entityService);
            }
        }

        return Collections.emptyList();
    }

    public static final class EncounterBuilderImpl extends EntityBuilderHelperImpl implements EncounterBuilder {
        private final Player player;
        private final Adventure adventure;
        private EncounterType type;
        private List<Item> items;
        private double sellRate;
        private double buyRate;

        public EncounterBuilderImpl(Long key, EntityService entityService, Player player, Adventure adventure) {
            super(key, entityService);
            this.player = player;
            this.adventure = adventure;
        }

        @Override
        public EncounterBuilder type(EncounterType type) {
            this.type = type;
            return this;
        }

        @Override
        public EncounterBuilder items(Item[] items) {
            this.items = Arrays.asList(items);
            return this;
        }

        @Override
        public EncounterBuilder buyRate(double buyRate) {
            this.buyRate = buyRate;
            return this;
        }

        @Override
        public EncounterBuilder sellRate(double sellRate) {
            this.sellRate = sellRate;
            return this;
        }

        @Override
        public Encounter build() {
            switch (type) {
                case ENCOUNTER_ENVIRONMENT:
                    return new EncounterEnvironmentImpl(key, name, this.buildDescriptors(), description, player,
                            adventure);
                case ENCOUNTER_MERCHANT:
                    return new EncounterMerchantImpl(key, name, this.buildDescriptors(), description, player, adventure,
                            items, sellRate, buyRate);
            }
            return null;
        }
    }
}
