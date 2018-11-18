package com.tompy.map;

import com.tompy.common.Builder;

public interface AdventureMapBuilder extends Builder<AdventureMap> {

    AdventureMapBuilder height(int height);

    AdventureMapBuilder width(int width);

    AdventureMapBuilder map(char[] map);

}
