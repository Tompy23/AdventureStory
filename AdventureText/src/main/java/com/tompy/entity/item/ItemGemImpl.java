package com.tompy.entity.item;

import java.util.List;

public class ItemGemImpl extends ItemImpl {

    public ItemGemImpl(Long key, String name, List<String> descriptors, String description, int manipulationTicks) {
        super(key, name, descriptors, description, manipulationTicks);
    }
}
