package com.tompy.common;

import java.io.Serializable;

public class Coordinates3DImpl implements Coordinates, Serializable {
    private static final long serialVersionUID = 1L;
    private final int x;
    private final int y;
    private final int z;

    public Coordinates3DImpl(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public double distance(Coordinates other) {
        return Math.sqrt(((other.getX() - x)^2) + ((other.getY() - y)^2) + ((other.getZ() - z)^2));
    }
}
