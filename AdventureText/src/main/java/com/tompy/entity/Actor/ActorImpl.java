package com.tompy.entity.Actor;

import com.tompy.adventure.Adventure;
import com.tompy.directive.Direction;
import com.tompy.entity.EntityService;
import com.tompy.entity.area.Area;
import com.tompy.entity.compartment.CompartmentBuilderHelperImpl;
import com.tompy.entity.compartment.CompartmentImpl;
import com.tompy.exit.Exit;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.tompy.attribute.Attribute.VISIBLE;
import static com.tompy.directive.EventType.EVENT_ACTOR_PROGRAM;

public class ActorImpl extends CompartmentImpl implements Actor {
    private static final Logger LOGGER = LogManager.getLogger(ActorImpl.class);

    private MoveStrategy moveStrategy;
    private Area currentArea;
    private int speedFactor;
    private int tickBank;

    protected ActorImpl(Long key, String name, List<String> descriptors, String description,
            EntityService entityService) {
        super(key, name, descriptors, description);
        entityService.add(this, VISIBLE);
    }

    public static ActorBuilder createBuilder(Long key, EntityService entityService) {
        return new ActorBuilderImpl(key, entityService);
    }

    @Override
    public List<Response> takeAction(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info(String.format("Actor [%s] taking action.", this.getName()));

        // The event manager will store all the actor's events which constitute its program.
        // Responses are only returned if the player is in the same area
        List<Response> responses = entityService.handle(this, EVENT_ACTOR_PROGRAM, player, adventure);
        return (player.getArea().equals(currentArea)) ? responses : Collections.emptyList();
    }

    @Override
    public void assignMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = Objects.requireNonNull(moveStrategy, "Move Strategy cannot be null.");
    }


    // There may be other "player like functions" added as well, like "take", etc.
    // These will be called from within Events with Actions specific to Actors
    //

    @Override
    public List<Response> move(Player player, Adventure adventure, EntityService entityService) {
        LOGGER.info(String.format("[%s] moving", name));

        Direction direction = moveStrategy.getMove(entityService);
        if (direction != null) {
            Exit exit = currentArea.getExitForDirection(direction);
            if (exit != null) {
                // Check speed factor
                int ticksAvailable = adventure.getCurrentActionTicks() + speedFactor + tickBank;
                LOGGER.info(
                        String.format("[%d]:action ticks + [%d]:speed factor + [%d]:tick bank = [%d]: ticks available",
                                adventure.getCurrentActionTicks(), speedFactor, tickBank, ticksAvailable));
                LOGGER.info(String.format("Exit pass thru ticks [%d]", exit.getPassThruTicks()));
                if (ticksAvailable >= 0 && exit.getPassThruTicks() <= ticksAvailable) {
                    Area area = exit.getConnectedArea(currentArea);
                    LOGGER.info(String.format("moved to [%s]", area.getName()));
                    setArea(area);
                    tickBank = ticksAvailable - exit.getPassThruTicks();
                } else {
                    tickBank = ticksAvailable;
                }
                LOGGER.info(String.format("[%d]:tick bank left over", tickBank));
                return Collections.singletonList(responseFactory.createBuilder().source(name).text("").build());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Area getArea() {
        return currentArea;
    }

    @Override
    public void setArea(Area area) {
        if (currentArea != null) {
            currentArea.removeActor(this);
        }
        this.currentArea = area;
        currentArea.addActor(this);
    }

    public static final class ActorBuilderImpl extends CompartmentBuilderHelperImpl implements ActorBuilder {

        public ActorBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        public Actor build() {
            return new ActorImpl(key, name, this.buildDescriptors(), description, entityService);
        }


        @Override
        public ActorBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public ActorBuilder description(String description) {
            this.description = description;
            return this;
        }
    }
}
