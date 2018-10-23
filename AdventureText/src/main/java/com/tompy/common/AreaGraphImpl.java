package com.tompy.common;

import com.tompy.entity.area.Area;
import com.tompy.exit.Exit;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaGraphImpl implements AreaGraph {
    private final Map<String, Area> areas = new HashMap<>();
    private final Map<Integer, Exit> exits =  new HashMap<>();

    private AreaGraphImpl(List<Area> buildAreas, List<Exit> buildExits) {
        buildAreas.stream().forEach((a) -> areas.put(a.getName(), a));
        buildExits.stream().forEach((e) -> exits.put(e.hashCode(), e));
    }

    @Override
    public void createBasicGraph() {

    }

    public static final class Builder implements AreaGraphBuilder {
        private List<Area> areas;
        private List<Exit> exits;

        @Override
        public AreaGraph build() {
            return new AreaGraphImpl(areas, exits);
        }


        @Override
        public AreaGraphBuilder areas(List<Area> areas) {
            this.areas = Collections.unmodifiableList(areas);
            return this;
        }

        @Override
        public AreaGraphBuilder exits(List<Exit> exits) {
            this.exits = Collections.unmodifiableList(exits);
            return this;
        }
    }
}
