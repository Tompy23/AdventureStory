package com.tompy.map;

import com.tompy.common.Coordinates;
import com.tompy.common.Coordinates2DImpl;
import com.tompy.response.Response;
import com.tompy.response.Responsive;

import java.util.*;

public class AdventureMapImpl extends Responsive implements AdventureMap {
    private final char[] mapChars;
    private final int width;
    private final int height;
    private Coordinates displayUpperLeft;
    private Coordinates displayLowerRight;
    private Map<Character, String> legendMap = new HashMap<>();
    private Map<Character, String> legendExtend = new HashMap<>();

    private AdventureMapImpl(int height, int width) {
        this.height = height;
        this.width = width;
        this.displayUpperLeft = new Coordinates2DImpl(0, 0);
        this.displayLowerRight = new Coordinates2DImpl(width - 1, height - 1);
        this.mapChars = new char[height * width];
        for (int i = 0; i < height * width; i++) {
            mapChars[i] = '.';
        }
        legendMap.put('.', "Clear");
    }

    private AdventureMapImpl(char[] map, int height, int width) {
        this.height = height;
        this.width = width;
        this.displayUpperLeft = new Coordinates2DImpl(0, 0);
        this.displayLowerRight = new Coordinates2DImpl(width - 1, height - 1);
        this.mapChars = Arrays.copyOf(map, map.length);
    }

    public static AdventureMapBuilder createBuilder() {
        return new AdventureMapBuilderImpl();
    }

    @Override
    public AdventureMap addLegend(char letter, String label) {
        legendExtend.put(letter, label);
        return this;
    }

    @Override
    public AdventureMap addLandmark(char letter, String label, Coordinates coordinates) {
        if (coordinateWithinMap(coordinates)) {
            int x = coordinates.getX();
            int y = coordinates.getY();
            mapChars[x + (height * y)] = letter;
            legendMap.put(letter, label);
        }

        return this;
    }

    @Override
    public AdventureMap addArea(char letter, String label, Coordinates upperLeft, Coordinates lowerRight) {
        if (coordinateWithinMap(upperLeft) && coordinateWithinMap(lowerRight)) {
            for (int i = upperLeft.getX(); i <= lowerRight.getX(); i++) {
                for (int j = upperLeft.getY(); j <= lowerRight.getY(); j++) {
                    mapChars[i + (height * j)] = letter;
                }
            }
            legendMap.put(letter, label);
        }
        return this;
    }

    @Override
    public void defineDisplay(Coordinates upperLeft, Coordinates lowerRight) {
        this.displayUpperLeft = upperLeft;
        this.displayLowerRight = lowerRight;
    }

    @Override
    public List<Response> display() {
        return display(this.displayUpperLeft, this.displayLowerRight);
    }

    @Override
    public List<Response> display(Coordinates upperLeft, Coordinates lowerRight) {
        List<Response> returnValue = new ArrayList<>();
        SortedSet<Character> legendChars = new TreeSet<>();
        int lineLength = lowerRight.getX() - upperLeft.getX() + 1;
        if (coordinateWithinMap(upperLeft) && coordinateWithinMap(lowerRight)) {
            for (int j = upperLeft.getY(); j <= lowerRight.getY(); j++) {
                char[] line = new char[lineLength];
                for (int i = 0; i < lineLength; i++) {
                    char c = mapChars[(i + upperLeft.getX()) + (width * j)];
                    line[i] = c;
                    legendChars.add(c);
                }
                returnValue.add(this.responseFactory.createBuilder().source("Map").text(new String(line)).build());
            }
            for (char key : legendChars) {
                if (legendMap.containsKey(key)) {
                    returnValue.add(this.responseFactory.createBuilder().source("Legend")
                            .text(String.format("%c -> %s", key, legendMap.get(key))).build());
                }
            }
            for (char key : legendExtend.keySet()) {
                returnValue.add(this.responseFactory.createBuilder().source("Legend")
                        .text(String.format("%c -> %s", key, legendExtend.get(key))).build());
            }
        }

        return returnValue;
    }

    @Override
    public void resetDisplay() {
        this.displayUpperLeft = new Coordinates2DImpl(0, 0);
        this.displayLowerRight = new Coordinates2DImpl(width - 1, height - 1);
    }

    private boolean coordinateWithinMap(Coordinates coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public static final class AdventureMapBuilderImpl implements AdventureMapBuilder {
        private int height;
        private int width;
        private char[] map = null;

        @Override
        public AdventureMap build() {
            if (map == null) {
                return new AdventureMapImpl(height, width);
            } else {
                return new AdventureMapImpl(map, height, width);
            }
        }

        @Override
        public AdventureMapBuilder height(int height) {
            this.height = height;
            return this;
        }

        @Override
        public AdventureMapBuilder width(int width) {
            this.width = width;
            return this;
        }

        @Override
        public AdventureMapBuilder map(char[] map) {
            this.map = Arrays.copyOf(map, map.length);
            return this;
        }
    }
}
