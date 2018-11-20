package com.tompy.map;

import com.tompy.common.Coordinates;
import com.tompy.response.Response;

import java.util.List;

public interface AdventureMap {

    AdventureMap addLegendExt(char letter, String label);

    AdventureMap addLegend(char letter, String label);

    AdventureMap addLandmark(char letter, String label, Coordinates coordinates);

    AdventureMap addArea(char letter, String label, Coordinates upperLeft, Coordinates lowerRight);

    void defineDisplay(Coordinates upperLeft, Coordinates lowerRight);

    List<Response> display();

    List<Response> display(Coordinates upperLeft, Coordinates lowerRight);

    void resetDisplay();
}
