package com.tompy.entity.encounter;

import com.tompy.entity.Entity;
import com.tompy.response.Response;

import java.util.List;
import java.util.Map;

/**
 * An interactive Entity
 */
public interface Encounter extends Entity {
    /**
     * Retrieves the options presented to the player
     * @return
     */
    Map<Long, String> getOptions();

    /**
     * Acts on the option selected
     * @param option
     * @return
     */
    List<Response> act(Long option);
}
