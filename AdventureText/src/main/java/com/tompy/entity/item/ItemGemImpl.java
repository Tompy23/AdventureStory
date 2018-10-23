package com.tompy.entity.item;

import com.tompy.entity.EntityService;

import java.util.List;

public class ItemGemImpl extends ItemImpl {

    public ItemGemImpl(Long key, String name, List<String> descriptors, String description,
                       EntityService entityService, int manipulationTicks) {
        super(key, name, descriptors, description, entityService, manipulationTicks);
    }
}
