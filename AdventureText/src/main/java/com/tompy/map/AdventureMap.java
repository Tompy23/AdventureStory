package com.tompy.map;

import com.tompy.adventure.Adventure;
import com.tompy.common.Coordinates;
import com.tompy.player.Player;
import com.tompy.response.Response;

import java.util.List;

public interface AdventureMap {

    AdventureMap addLegendExt(char letter, String label);

    AdventureMap removeLegendExt(char letter);

    AdventureMap addLegend(char letter, String label);

    AdventureMap addLandmark(char letter, String label, Coordinates coordinates);

    AdventureMap addArea(char letter, String label, Coordinates upperLeft, Coordinates lowerRight);

    void defineDisplay(Coordinates upperLeft, Coordinates lowerRight);

    List<Response> display(Player player, Adventure adventure);

    List<Response> display(Player player, Adventure adventure, Coordinates upperLeft, Coordinates lowerRight);

    void resetDisplay();

    void addOverlay(MapOverlay overlay);

    void removeOverlay(MapOverlay overlay);
}
