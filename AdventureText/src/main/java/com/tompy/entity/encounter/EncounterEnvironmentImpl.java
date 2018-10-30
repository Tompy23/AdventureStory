package com.tompy.entity.encounter;

import com.tompy.adventure.Adventure;
import com.tompy.player.Player;

import java.util.List;

public class EncounterEnvironmentImpl extends EncounterImpl implements Encounter {

    public EncounterEnvironmentImpl(Long key, String name, List<String> descriptors, String description, Player player,
            Adventure adventure) {
        super(key, name, descriptors, description, player, adventure);
    }
}
