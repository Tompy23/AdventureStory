package com.tompy.adventure;

import com.tompy.directive.Direction;
import com.tompy.entity.Actor.MoveStrategyFactory;
import com.tompy.entity.EntityFacadeBuilderFactory;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.exit.ExitBuilderFactory;
import com.tompy.io.UserInput;
import com.tompy.player.Player;
import com.tompy.state.AdventureState;
import com.tompy.state.AdventureStateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.Objects;

public abstract class AdventureImpl extends AdventureHelper implements Adventure {
    private static final Logger LOGGER = LogManager.getLogger(AdventureImpl.class);
    protected final EntityFacadeBuilderFactory entityFacadeBuilderFactory;
    private final UserInput userInput;
    private final PrintStream outStream;
    private boolean proceed;
    private AdventureState currentState;
    private int currentTick;
    private int actionTicks;


    public AdventureImpl(Player player, EntityService entityService,
            EntityFacadeBuilderFactory entityFacadeBuilderFactory, ExitBuilderFactory exitBuilderFactory,
            UserInput userInput, PrintStream outStream) {
        super(player, entityService, exitBuilderFactory);
        this.entityFacadeBuilderFactory =
                Objects.requireNonNull(entityFacadeBuilderFactory, "Entity Facade Builder Factory cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "User Input cannot be null.");
        this.outStream = Objects.requireNonNull(outStream, "Output Stream cannot be null.");
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
    public UserInput getUI() {
        return userInput;
    }

    @Override
    public PrintStream getOutStream() {
        return outStream;
    }

    @Override
    public void start(AdventureState state, String startRoom, Direction direction) {
        LOGGER.info("Starting the adventure.");
        Area startArea = entityService.getAreaByName(startRoom);
        player.setArea(startArea);
        startArea.enter(direction, player, this).stream().forEachOrdered((a) -> outStream.println(a.render()));
        outStream.println();

        proceed = true;
        changeState(state);
        processState();
    }

    @Override
    public void stop() {
        LOGGER.info("Stopping the adventure.");
        //TODO Persist
        proceed = false;
    }

    @Override
    public void process() {
        if (currentState != null) {
            currentState.process();
        }
    }

    @Override
    public void changeState(AdventureState newState) {
        if (currentState != null) {
            currentState.end();
        }
        currentState = newState;
        currentState.start();
    }

    private void processState() {
        if (currentState != null) {
            while (proceed) {
                LOGGER.info(String.format("Start round.  Current ticks [%d]", getCurrentTicks()));
                setActionTicks(0);
                currentState.process();
                LOGGER.info(
                        String.format("End round.  Ticks + [%d] -> [%d]", getCurrentActionTicks(), getCurrentTicks()));
            }
            currentState.end();
        }
    }

    @Override
    public int getCurrentTicks() {
        return currentTick;
    }

    @Override
    public void setActionTicks(int ticks) {
        actionTicks =+ ticks;
    }

    @Override
    public int getCurrentActionTicks() {
        return actionTicks;
    }

    @Override
    public void endAction() {
        currentTick = +actionTicks;
        actionTicks = 0;
    }
}