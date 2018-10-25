package com.tompy.attribute;

import com.tompy.attribute.Attribute;
import com.tompy.attribute.AttributeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;

public class AttributeManagerImpl implements AttributeManager, Serializable {
    private static final Logger LOGGER = LogManager.getLogger(AttributeManagerFactoryImpl.class);

    private Map<Attribute, Integer> managed;
    private Map<Attribute, String> doesApply;
    private Map<Attribute, String> doesNotApply;

    public AttributeManagerImpl() {
        managed = new HashMap<>();
        doesApply = new HashMap<>();
        doesNotApply = new HashMap<>();
    }

    @Override
    public void clear() {
        managed.clear();
    }

    @Override
    public void add(Attribute attribute) {
        add(attribute, 1);
    }

    @Override
    public void add(Attribute attribute, Integer value) {
        LOGGER.info("Adding attribute [{}] with value [{}]", attribute.name(), value);
        if (managed.containsKey(attribute)) {
            if (attribute.stackable()) {
                managed.put(attribute, managed.get(attribute) + value);
            }
        } else {
            managed.put(attribute, attribute.hasValue() || attribute.stackable() ? value : null);
        }
    }

    @Override
    public void remove(Attribute attribute) {
        LOGGER.info("Removing attribute [{}]", attribute.name());
        if (managed.containsKey(attribute)) {
            if (attribute.stackable()) {
                if (managed.get(attribute) > 1) {
                    managed.put(attribute, managed.get(attribute) - 1);
                } else {
                    managed.remove(attribute);
                }
            } else {
                managed.remove(attribute);
            }
        }
    }

    @Override
    public void reset(Attribute attribute) {
        if (managed.containsKey(attribute)) {
            managed.remove(attribute);
            managed.put(attribute, 0);
        }
    }

    @Override
    public Set<Attribute> getAll() {
        return Collections.unmodifiableSet(managed.keySet());
    }

    @Override
    public OptionalInt getValue(Attribute attribute) {
        if (managed.containsKey(attribute)) {
            return attribute.hasValue() || attribute.stackable() ? OptionalInt.of(managed.get(attribute)) :
                   OptionalInt.empty();
        } else {
            return OptionalInt.empty();
        }
    }

    @Override
    public boolean is(Attribute attribute) {
        return managed.keySet().contains(attribute);
    }

    @Override
    public void addApply(Attribute attribute, String text) {
        this.doesApply.put(attribute, text);
    }

    @Override
    public void addDesNotApply(Attribute attribute, String text) {
        this.doesNotApply.put(attribute, text);
    }

    @Override
    public String getApplication(Attribute attribute, boolean apply) {
        return apply ? doesApply.get(attribute) : doesNotApply.get(attribute);
    }
}
