package com.tompy.entity.event;

public class EventManagerFactoryImpl implements EventManagerFactory {

    @Override
    public EventManager create() {
        return new EventManagerImpl();
    }
}
