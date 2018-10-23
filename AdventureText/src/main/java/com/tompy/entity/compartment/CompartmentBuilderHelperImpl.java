package com.tompy.entity.compartment;

import com.tompy.entity.EntityService;
import com.tompy.entity.EntityBuilderHelperImpl;
import com.tompy.entity.item.Item;

import java.util.ArrayList;
import java.util.List;

public class CompartmentBuilderHelperImpl extends EntityBuilderHelperImpl {
    protected List<Item> items;

    protected CompartmentBuilderHelperImpl(Long key, EntityService entityService) {
        super(key, entityService);
        this.items = new ArrayList<>();
    }
}
