package com.tompy.state;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.io.UserInput;
import com.tompy.player.Player;

import java.io.PrintStream;
import java.util.Objects;

public class StateCombatImpl extends AdventureStateBaseImpl implements AdventureState {

    public StateCombatImpl(Player player, Adventure adventure, UserInput userInput, PrintStream outStream,
            EntityService entityService) {
        super(player, adventure, userInput, outStream, entityService);
    }

    public static AdventureStateCombatBuilder createBuilder(Player player, Adventure adventure, UserInput userInput,
            PrintStream outputStream, EntityService entityService) {
        return new AdventureStateCombatBuilderImpl(player, adventure, userInput, outputStream, entityService);
    }

    @Override
    public void start() {

    }

    @Override
    public void process(EntityService entityService) {

    }

    @Override
    public void end() {

    }

    public static final class AdventureStateCombatBuilderImpl implements AdventureStateCombatBuilder {
        private final Player player;
        private final Adventure adventure;
        private final UserInput userInput;
        private final PrintStream outputStream;
        private final EntityService entityService;

        public AdventureStateCombatBuilderImpl(Player player, Adventure adventure, UserInput userInput,
                PrintStream outputStream, EntityService entityService) {
            this.player = Objects.requireNonNull(player, "Player cannot be null.");
            this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
            this.userInput = Objects.requireNonNull(userInput, "UserInput cannot be null.");
            this.outputStream = Objects.requireNonNull(outputStream, "Output Stream cannot be null.");
            this.entityService = Objects.requireNonNull(entityService, "Entity Service cannot be null.");
        }

        @Override
        public AdventureState build() {
            return new StateCombatImpl(player, adventure, userInput, outputStream, entityService);
        }
    }
}
