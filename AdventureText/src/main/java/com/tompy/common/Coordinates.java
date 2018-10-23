package com.tompy.common;

public interface Coordinates {
    int getX();
    int getY();
    int getZ();
    double distance(Coordinates other);
}
