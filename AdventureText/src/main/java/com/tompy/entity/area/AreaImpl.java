package com.tompy.entity.area;

import com.tompy.adventure.Adventure;
import com.tompy.adventure.AdventureUtils;
import com.tompy.common.Coordinates;
import com.tompy.directive.Direction;
import com.tompy.directive.EventType;
import com.tompy.entity.Actor.Actor;
import com.tompy.entity.EntityService;
import com.tompy.entity.compartment.CompartmentBuilderHelperImpl;
import com.tompy.entity.compartment.CompartmentImpl;
import com.tompy.entity.feature.Feature;
import com.tompy.exit.Exit;
import com.tompy.player.Player;
import com.tompy.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.tompy.attribute.Attribute.VISIBLE;
import static com.tompy.directive.EventType.*;

public class AreaImpl extends CompartmentImpl implements Area {
    private static final Logger LOGGER = LogManager.getLogger(AreaImpl.class);
    protected final String searchDescription;
    protected Map<Direction, Exit> exits = new HashMap<>();
    protected List<Feature> features = new ArrayList<>();
    protected Map<Direction, List<Feature>> directionFeatures = new HashMap<>();
    protected List<Actor> actors = new ArrayList<>();
    protected int searchTicks;
    protected Coordinates coordinates;

    protected AreaImpl(Long key, String name, List<String> descriptors, String description, String searchDescription,
            int searchTicks, Coordinates coordinates) {
        super(key, name, descriptors, description);
        this.searchDescription = searchDescription;
        this.searchTicks = searchTicks;
        this.coordinates = Objects.requireNonNull(coordinates, "Coordinates cannot be null.");
    }


    public static AreaBuilder createBuilder(Long key, EntityService entityService) {
        return new AreaBuilderImpl(key, entityService);
    }

    @Override
    public void installExit(Direction direction, Exit exit) {
        Objects.requireNonNull(exit, "When initializing an exit to a room, the exit must not be null");

        LOGGER.info("Installing Exit from [{}] to area [{}]",
                new String[]{this.getName(), exit.getConnectedArea(this).getName()});

        // Next we add the exit
        exits.put(direction, exit);
    }

    @Override
    public void installFeature(Feature feature, Direction direction) {
        LOGGER.info("Installing feature [{}] in [{}]", new String[]{feature.getName(), this.getName()});
        if (null != direction) {
            LOGGER.info("Installing feature in direction [{}]", direction.name());
            if (!directionFeatures.containsKey(direction)) {
                directionFeatures.put(direction, new ArrayList<>());
            }
            directionFeatures.get(direction).add(feature);
        } else {
            features.add(feature);
        }
    }

    @Override
    public Exit getExitForDirection(Direction direction) {
        return exits.get(direction);
    }

    @Override
    public List<Response> enter(Direction direction, Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Entering room [{}]", this.getName());

        returnValue
                .addAll(entityService.handle(this, AdventureUtils.getAreaEnterEventType(direction), player, adventure));
        returnValue.addAll(entityService.handle(this, EVENT_AREA_ENTER, player, adventure));

        if (!items.isEmpty()) {
            items.stream().filter((i) -> entityService.is(i, VISIBLE)).forEach((i) -> returnValue
                    .add(this.responseFactory.createBuilder().source(i.getName()).text(i.getDescription()).build()));
        }

        player.visitArea(name);

        return returnValue;
    }

    @Override
    public List<Response> exit(Direction direction, Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Exiting room [{}] in direction [{}]", this.getName(), direction.name());

        returnValue.addAll(entityService.handle(this, EVENT_AREA_EXIT, player, adventure));
        returnValue
                .addAll(entityService.handle(this, AdventureUtils.getAreaExitEventType(direction), player, adventure));

        return returnValue;
    }

    @Override
    public List<Response> search(Player player, Adventure adventure, EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching room [{}]", this.getName());

        returnValue.addAll(entityService.handle(this, EVENT_AREA_SEARCH, player, adventure));

        if (!features.isEmpty()) {
            List<Response> featureSearch = new ArrayList<>();
            features.stream().filter((i) -> entityService.is(i, VISIBLE))
                    .forEach((f) -> featureSearch.addAll(f.search(player, adventure, entityService)));
            if (!featureSearch.isEmpty()) {
                returnValue.addAll(entityService.handle(this, EVENT_AREA_PRE_FEATURE_SEARCH, player, adventure));
                returnValue.addAll(featureSearch);
            }
        }

        if (!items.isEmpty()) {
            List<Response> itemSearch = new ArrayList<>();
            items.stream().filter((i) -> entityService.is(i, VISIBLE)).forEach((i) -> itemSearch
                    .add(this.responseFactory.createBuilder().source(i.getName()).text(i.getDescription()).build()));
            if (!itemSearch.isEmpty()) {
                returnValue.addAll(entityService.handle(this, EVENT_AREA_PRE_ITEM_SEARCH, player, adventure));
                returnValue.addAll(itemSearch);
            }
        }

        if (!actors.isEmpty()) {
            List<Response> actorSearch = new ArrayList<>();
            actors.stream().filter((a) -> entityService.is(a, VISIBLE)).forEach(
                    (a) -> actorSearch.add(responseFactory.createBuilder().source(name).text(a.getName()).build()));
            if (!actorSearch.isEmpty()) {
                returnValue.add(responseFactory.createBuilder().source(name).text("Others...").build());
                returnValue.addAll(actorSearch);
            }
        }

        player.searchArea(name);

        return returnValue;
    }

    @Override
    public List<Response> searchDirection(Direction direction, Player player, Adventure adventure,
            EntityService entityService) {
        List<Response> returnValue = new ArrayList<>();
        LOGGER.info("Searching room [{}] in direction [{}]", this.getName(), direction.name());

        EventType type = AdventureUtils.getAreaSearchEventType(direction);
        returnValue.addAll(entityService.handle(this, type, player, adventure));

        if (directionFeatures.containsKey(direction)) {
            if (!directionFeatures.get(direction).isEmpty()) {
                List<Response> featureSearch = new ArrayList<>();
                directionFeatures.get(direction).stream()
                        .forEach((f) -> featureSearch.addAll(f.search(player, adventure, entityService)));
                if (!featureSearch.isEmpty()) {
                    returnValue.addAll(entityService
                            .handle(this, EVENT_AREA_PRE_FEATURE_DIRECTION_SEARCH, player, adventure));
                    returnValue.addAll(featureSearch);
                }

            }
        }

        return returnValue;
    }

    @Override
    public List<Feature> getAllFeatures() {
        LOGGER.info("Retrieving all features from room [{}]", this.getName());
        List<Feature> returnValue = new ArrayList<>();
        returnValue.addAll(features);
        directionFeatures.values().stream().forEach(returnValue::addAll);

        return returnValue;
    }

    @Override
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    @Override
    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    @Override
    public boolean isActor(Actor actor) {
        return actors.contains(actor);
    }

    @Override
    public List<Actor> getAllActors() {
        return Collections.unmodifiableList(actors);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Area) {
            return this.getKey().equals(((Area) other).getKey());
        }
        return false;
    }

    public static class AreaBuilderImpl extends CompartmentBuilderHelperImpl implements AreaBuilder {
        protected String name;
        protected String description;
        protected String searchDescription;
        protected String compartmentName;
        protected String compartmentDescription;
        protected int searchTicks;
        protected Coordinates coordinates;

        public AreaBuilderImpl(Long key, EntityService entityService) {
            super(key, entityService);
        }

        @Override
        public AreaBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public AreaBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public AreaBuilder searchDescription(String searchDescription) {
            this.searchDescription = searchDescription;
            return this;
        }

        @Override
        public AreaBuilder compartmentName(String compartmentName) {
            this.compartmentName = compartmentName;
            return this;
        }

        @Override
        public AreaBuilder compartmentDescription(String compartmentDescription) {
            this.compartmentDescription = compartmentDescription;
            return this;
        }

        @Override
        public AreaBuilder searchTicks(int searchTicks) {
            this.searchTicks = searchTicks;
            return this;
        }

        @Override
        public AreaBuilder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        @Override
        public Area build() {
            return new AreaImpl(key, name, this.buildDescriptors(), description, searchDescription, searchTicks,
                    coordinates);
        }
    }
}
