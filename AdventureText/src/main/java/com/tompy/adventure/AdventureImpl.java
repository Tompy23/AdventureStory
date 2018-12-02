package com.tompy.adventure;

import com.tompy.directive.Direction;
import com.tompy.entity.Actor.MoveStrategyFactory;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.exit.ExitBuilderFactory;
import com.tompy.io.UserIO;
import com.tompy.map.AdventureMap;
import com.tompy.map.AdventureMapBuilderFactory;
import com.tompy.persistence.AdventureData;
import com.tompy.player.Player;
import com.tompy.state.AdventureState;
import com.tompy.state.AdventureStateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AdventureImpl extends AdventureHelper implements Adventure {
    private static final Logger LOGGER = LogManager.getLogger(AdventureImpl.class);
    private UserIO userIO;
    private boolean proceed;
    private AdventureState currentState;
    private int currentTick;
    private int actionTicks;
    private static Map<String, AdventureMap> mapOfMaps = new HashMap<>();

    @Override
    public void init(Player player, EntityService entityService, ExitBuilderFactory exitBuilderFactory,
            AdventureMapBuilderFactory mapBuilderFactory, UserIO userInput, String propertiesFilename) {
        super.init(player, entityService, exitBuilderFactory, mapBuilderFactory, propertiesFilename);
        this.userIO = Objects.requireNonNull(userInput, "User Input cannot be null.");
    }

    @Override
    public void setStateFactory(AdventureStateFactory stateFactory) {
        this.stateFactory = Objects.requireNonNull(stateFactory, "State Factory cannot be null.");
    }

    @Override
    public UserIO getUI() {
        return userIO;
    }

    public void addMap(String key, AdventureMap map) {
        mapOfMaps.put(key, map);
    }

    public AdventureMap getMap(String key) {
        return mapOfMaps.get(key);
    }

    @Override
    public void start(AdventureState state, String startRoom, Direction direction, EntityService entityService) {
        LOGGER.info("Starting the adventure.");
        Area startArea = null;
        if (startRoom != null) {
            startArea = entityService.getAreaByName(startRoom);
            player.setArea(startArea);
        }
        if (direction != null && startArea != null) {
            startArea.enter(direction, player, this, entityService).stream()
                    .forEachOrdered((a) -> userIO.println(a.render()));
            userIO.println();
        }

        proceed = true;
        changeState(state, entityService);
        processState(entityService);
    }

    @Override
    public void stop() {
        LOGGER.info("Stopping the adventure.");
        //TODO Persist
        proceed = false;
    }

    @Override
    public void changeState(AdventureState newState, EntityService entityService) {
        if (currentState != null) {
            currentState.end(entityService, userIO);
        }
        currentState = newState;
        currentState.start(entityService, userIO);
    }

    private void processState(EntityService entityService) {
        if (currentState != null) {
            while (proceed) {
                LOGGER.info(String.format("Start round.  Current ticks [%d]", getCurrentTicks()));
                currentState.process(entityService, userIO);
                endAction();
                LOGGER.info(
                        String.format("End round.  Ticks + [%d] -> [%d]", getCurrentActionTicks(), getCurrentTicks()));
            }
            currentState.end(entityService, userIO);
        }
    }

    @Override
    public int getCurrentTicks() {
        return currentTick;
    }

    @Override
    public void setActionTicks(int ticks) {
        actionTicks += ticks;
    }

    @Override
    public int getCurrentActionTicks() {
        return actionTicks;
    }

    @Override
    public void endAction() {
        currentTick += actionTicks;
        actionTicks = 0;
    }

    @Override
    public AdventureState getStartingState() {
        if (stateFactory != null) {
            return stateFactory.getExploreState();
        } else {
            return null;
        }
    }

    @Override
    public void load(AdventureData data) {
        // TODO - disperse the data... create EntityService, et al.

    }

    @Override
    public AdventureData save() {
        AdventureData.AdventureDataBuilder builder = new AdventureData.AdventureDataBuilder();
        builder.actors(entityService.getActors());
        builder.actionTicks(actionTicks);
        builder.areas(entityService.getAreas());
        builder.attributeManagers(entityService.getAttributeManagers());
        builder.currentState(currentState);
        builder.encounters(entityService.getEncounters());
        builder.currentTick(currentTick);
        builder.entityKey(entityService.getEntityKey());
        builder.eventManagers(entityService.getEventManagers());
        builder.events(entityService.getEvents());
        builder.entityMap(entityService.getEntityMap());
        builder.features(entityService.getFeatures());
        builder.items(entityService.getItems());
        builder.player(player);
        builder.maps(mapOfMaps);

        return builder.build();
    }
}