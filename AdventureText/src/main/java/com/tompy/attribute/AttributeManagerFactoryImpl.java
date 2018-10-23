package com.tompy.attribute;

public class AttributeManagerFactoryImpl implements AttributeManagerFactory {

    @Override
    public AttributeManager create() {
        return new AttributeManagerImpl();
    }
}
