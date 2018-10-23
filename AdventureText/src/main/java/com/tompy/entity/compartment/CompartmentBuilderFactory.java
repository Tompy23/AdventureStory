package com.tompy.entity.compartment;

public interface CompartmentBuilderFactory {

    /**
     * Creates a new compartment Builder
     *
     * @return - the builder
     */
    CompartmentBuilder createCompartmentBuilder();

    void addCompartment(Compartment compartment);
}
