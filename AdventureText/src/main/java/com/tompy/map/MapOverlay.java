package com.tompy.map;

import com.tompy.adventure.Adventure;
import com.tompy.player.Player;

public interface MapOverlay {
    char[] overlay(char[] map, int height, int width, Player player, Adventure adventure);

    void whenRemove(AdventureMap map);

    void whenAdd(AdventureMap map);
}
