package com.tompy.common;

public class Coordinates2DImpl implements Coordinates {
    private final int x;
    private final int y;

    public Coordinates2DImpl(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public double distance(Coordinates other) {
        return Math.sqrt(((other.getX() - x)^2) + ((other.getY() - y)^2));
    }
}
