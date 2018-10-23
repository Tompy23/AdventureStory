package com.tompy.state;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.io.UserInput;
import com.tompy.player.Player;

import java.io.PrintStream;
import java.util.Objects;

public abstract class AdventureStateBaseImpl implements AdventureState {
    protected final Player player;
    protected final Adventure adventure;
    protected final UserInput userInput;
    protected final PrintStream outStream;
    protected final EntityService entityService;

    public AdventureStateBaseImpl(Player player, Adventure adventure, UserInput userInput, PrintStream outStream,
            EntityService entityService) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
        this.userInput = Objects.requireNonNull(userInput, "User Input cannot be null.");
        this.outStream = Objects.requireNonNull(outStream, "Output Stream cannot be null.");
        this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
    }
}
