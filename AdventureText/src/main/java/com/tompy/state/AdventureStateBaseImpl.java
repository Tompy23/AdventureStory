package com.tompy.state;

import com.tompy.adventure.Adventure;
import com.tompy.io.UserIO;
import com.tompy.player.Player;

import java.io.Serializable;
import java.util.Objects;

public abstract class AdventureStateBaseImpl implements AdventureState, Serializable {
    private static final long serialVersionUID = 1L;
    protected final Player player;
    protected final Adventure adventure;

    public AdventureStateBaseImpl(Player player, Adventure adventure) {
        this.player = Objects.requireNonNull(player, "Player cannot be null.");
        this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
    }
}
