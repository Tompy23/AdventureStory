package com.tompy.map;

import com.tompy.adventure.Adventure;
import com.tompy.common.Coordinates;
import com.tompy.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class MapOverlayPlayerLocationImpl implements MapOverlay, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(MapOverlayPlayerLocationImpl.class);

    private final char mark;
    private final String legend;

    private MapOverlayPlayerLocationImpl(char mark, String legend) {
        this.mark = mark;
        this.legend = Objects.requireNonNull(legend, "Legend cannot be null.");
    }

    @Override
    public char[] overlay(char[] map, int height, int width, Player player, Adventure adventure) {
        Coordinates playerLocation = player.getArea().getCoordinates();
        char[] newMap = Arrays.copyOf(map, map.length);
        if (playerLocation.getX() < width && playerLocation.getY() < height) {
            newMap[height * playerLocation.getY() + playerLocation.getX()] = mark;
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

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private char mark;
        private String legend;

        public MapOverlayPlayerLocationImpl build() {
            return new MapOverlayPlayerLocationImpl(mark, legend);
        }

        Builder mark(char mark) {
            this.mark = mark;
            return this;
        }

        Builder legend(String legend) {
            this.legend = legend;
            return this;
        }
    }
}
