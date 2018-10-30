package com.tompy.state;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.io.UserIO;
import com.tompy.player.Player;

import java.io.PrintStream;
import java.util.Objects;

public class StateCombatImpl extends AdventureStateBaseImpl implements AdventureState {

    public StateCombatImpl(Player player, Adventure adventure) {
        super(player, adventure);
    }

    public static AdventureStateCombatBuilder createBuilder(Player player, Adventure adventure) {
        return new AdventureStateCombatBuilderImpl(player, adventure);
    }

    @Override
    public void start(EntityService entityService, UserIO io) {

    }

    @Override
    public void process(EntityService entityService, UserIO io) {

    }

    @Override
    public void end(EntityService entityService, UserIO io) {

    }

    public static final class AdventureStateCombatBuilderImpl implements AdventureStateCombatBuilder {
        private final Player player;
        private final Adventure adventure;

        public AdventureStateCombatBuilderImpl(Player player, Adventure adventure) {
            this.player = Objects.requireNonNull(player, "Player cannot be null.");
            this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
        }

        @Override
        public AdventureState build() {
            return new StateCombatImpl(player, adventure);
        }
    }
}
