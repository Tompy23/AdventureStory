package com.tompy.adventure;

import com.tompy.directive.Direction;
import com.tompy.entity.Actor.MoveStrategyFactory;
import com.tompy.entity.Actor.MoveStrategyFactoryImpl;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.exit.ExitBuilderFactory;
import com.tompy.io.UserIO;
import com.tompy.persistence.AdventureData;
import com.tompy.player.Player;
import com.tompy.state.AdventureState;
import com.tompy.state.AdventureStateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public abstract class AdventureImpl extends AdventureHelper implements Adventure {
    private static final Logger LOGGER = LogManager.getLogger(AdventureImpl.class);
    private final UserIO userIO;
    private boolean proceed;
    private AdventureState currentState;
    private int currentTick;
    private int actionTicks;

    public AdventureImpl() {
        userIO = null;
    }

    public AdventureImpl(Player player, EntityService entityService, ExitBuilderFactory exitBuilderFactory,
            UserIO userInput) {
        super(player, entityService, exitBuilderFactory);
        this.userIO = Objects.requireNonNull(userInput, "User Input cannot be null.");
    }

    @Override
    public void setStateFactory(AdventureStateFactory stateFactory) {
        this.stateFactory = Objects.requireNonNull(stateFactory, "State Factory cannot be null.");
    }

    @Override
    public void setMoveStrategyFactory(MoveStrategyFactory moveStrategyFactory) {
        this.moveStrategyFactory = Objects.requireNonNull(moveStrategyFactory, "Move Strategy Factory cannot be null.");
    }

    @Override
    public UserIO getUI() {
        return userIO;
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
        // TODO - Create the AdventureData from EntityService, et al and return.
        return builder.build();
    }
}