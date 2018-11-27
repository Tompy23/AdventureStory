package com.tompy.map;

import com.tompy.adventure.Adventure;
import com.tompy.common.Coordinates;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MapOverlaySplatterImpl implements MapOverlay, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(MapOverlaySplatterImpl.class);

    private final char mark;
    private final List<Coordinates> spots;
    private final String legend;

    private MapOverlaySplatterImpl(char mark, List<Coordinates> spots, String legend) {
        this.mark = mark;
        this.spots = spots;
        this.legend = Objects.requireNonNull(legend, "Legend cannot be null.");
    }

    public static MapOverlaySplatterImpl.Builder createBuilder() {
        return new MapOverlaySplatterImpl.Builder();
    }

    @Override
    public char[] overlay(char[] map, int height, int width, Player player, Adventure adventure) {
        char[] newMap = Arrays.copyOf(map, map.length);
        for (Coordinates spot : spots) {
            if (spot.getX() < width && spot.getY() < height) {
                newMap[height * spot.getY() + spot.getX()] = mark;
            }
        }

        return newMap;
    }

    @Override
    public void whenRemove(AdventureMap map) {
        map.removeLegendExt(mark);
    }

    @Override
    public void whenAdd(AdventureMap map) {
        map.addLegendExt(mark, legend);
    }

    public static final class Builder {
        private char mark;
        private String legend;
        private List<Coordinates> spots = new ArrayList<>();

        public MapOverlaySplatterImpl build() {
            return new MapOverlaySplatterImpl(mark, spots, legend);
        }

        MapOverlaySplatterImpl.Builder mark(char mark) {
            this.mark = mark;
            return this;
        }

        MapOverlaySplatterImpl.Builder spot(Coordinates spot) {
            spots.add(spot);
            return this;
        }

        MapOverlaySplatterImpl.Builder legend(String legend) {
            this.legend = legend;
            return this;
        }
    }
}
