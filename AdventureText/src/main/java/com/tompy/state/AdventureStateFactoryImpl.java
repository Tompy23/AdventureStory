package com.tompy.state;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.io.UserInput;
import com.tompy.player.Player;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Objects;

public class AdventureStateFactoryImpl implements AdventureStateFactory, Serializable {
    private static final long serialVersionUID = 1L;
    private final Player player;
    private final Adventure adventure;
    private final UserInput userInput;
    private final PrintStream outputStream;
    private final EntityService entityService;
    private AdventureState explore = null;


    public AdventureStateFactoryImpl(Player player, Adventure adventure, UserInput userInput, PrintStream outputStream,
            EntityService entityService) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "UserInput cannot be null.");
        this.outputStream = Objects.requireNonNull(outputStream, "Output Stream cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        adventure.setStateFactory(this);
    }

    @Override
    public AdventureState getExploreState() {
        if (explore == null) {
            explore = new StateExploreImpl(player, adventure, userInput, outputStream, entityService);
        }
        return explore;
    }

    @Override
    public AdventureStateEncounterBuilder createEncounterState() {
        return StateEncounterImpl.createBuilder(player, adventure, userInput, outputStream, entityService);
    }

    @Override
    public AdventureStateCombatBuilder createCombatState() {
        return StateCombatImpl.createBuilder(player, adventure, userInput, outputStream, entityService);
    }
}
