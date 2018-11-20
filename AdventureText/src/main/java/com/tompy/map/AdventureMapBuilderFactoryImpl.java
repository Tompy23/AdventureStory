package com.tompy.map;

public class AdventureMapBuilderFactoryImpl implements AdventureMapBuilderFactory {
    @Override
    public AdventureMapBuilder adventureMapBuilder() {
        return AdventureMapImpl.createBuilder();
    }
}
