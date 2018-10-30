package com.tompy.state;

import com.tompy.adventure.Adventure;
import com.tompy.entity.EntityService;
import com.tompy.entity.encounter.Encounter;
import com.tompy.io.UserIO;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.tompy.directive.EventType.EVENT_ENCOUNTER_END;
import static com.tompy.directive.EventType.EVENT_ENCOUNTER_START;

public class StateEncounterImpl extends AdventureStateBaseImpl implements AdventureState {
    private final static Logger LOGGER = LogManager.getLogger(StateEncounterImpl.class);
    private final Encounter encounter;

    public StateEncounterImpl(Player player, Adventure adventure, Encounter encounter) {
        super(player, adventure);
        this.encounter = Objects.requireNonNull(encounter, "Encounter cannot be null.");
    }

    public static AdventureStateEncounterBuilder createBuilder(Player player, Adventure adventure) {
        return new AdventureStateEncounterBuilderImpl(player, adventure);
    }

    @Override
    public void start(EntityService entityService, UserIO io) {
        LOGGER.info("Encounter [{}] state start", encounter.getName());
        entityService.handle(encounter, EVENT_ENCOUNTER_START, player, adventure).stream()
                .forEachOrdered((a) -> io.println(a.render()));

    }

    @Override
    public void process(EntityService entityService, UserIO io) {
        // Call the encounter's "list options", returns Map<Long, String>
        Map<Long, String> options = encounter.getOptions(entityService);

        // Call UserIO Make choice, returns Long
        Long option = io.getSelection(options);

        // Call the encounter and pass the Long selected, returns List<Response>
        List<Response> responses = encounter.act(option, entityService);

        // Render the list of responses to outStream
        responses.stream().forEachOrdered((r) -> io.println(r.render()));
        io.println();
    }

    @Override
    public void end(EntityService entityService, UserIO io) {
        LOGGER.info("Encounter [{}] state end.", encounter.getName());
        entityService.handle(encounter, EVENT_ENCOUNTER_END, player, adventure).stream()
                .forEachOrdered((a) -> io.println(a.render()));
    }

    public static final class AdventureStateEncounterBuilderImpl implements AdventureStateEncounterBuilder {
        private final Player player;
        private final Adventure adventure;
        private Encounter encounter;


        public AdventureStateEncounterBuilderImpl(Player player, Adventure adventure) {
            this.player = Objects.requireNonNull(player, "Player cannot be null.");
            this.adventure = Objects.requireNonNull(adventure, "Adventure cannot be null.");
        }

        @Override
        public AdventureState build() {
            return new StateEncounterImpl(player, adventure, encounter);
        }

        @Override
        public AdventureStateEncounterBuilder encounter(Encounter encounter) {
            this.encounter = encounter;
            return this;
        }
    }
}
