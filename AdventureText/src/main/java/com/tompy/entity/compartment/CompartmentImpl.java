package com.tompy.entity.compartment;

import com.tompy.entity.EntityService;
import com.tompy.entity.EntityImpl;
import com.tompy.entity.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class CompartmentImpl extends EntityImpl implements Compartment {
    public static final Logger LOGGER = LogManager.getLogger(CompartmentImpl.class);
    protected final List<Item> items;

    protected CompartmentImpl(Long key, String name, List<String> descriptors, String description,
                              EntityService entityService) {
        super(key, name, descriptors, description, entityService);
        items = new ArrayList<>();
    }

    @Override
    public List<Item> getAllItems() {
        LOGGER.info("Get all items from compartment [{}]", this.getName());
        return Collections.unmodifiableList(items);
    }

    @Override
    public void addItem(Item item) {
        LOGGER.info("Add Item [{}] to compartment [{}]", item.getName(), this.getName());
        items.add(item);
    }

    @Override
    public void removeItem(Item item) {
        LOGGER.info("Remove item [{}] from compartment [{}]", item.getName(), this.getName());
        items.remove(item);
    }
}
