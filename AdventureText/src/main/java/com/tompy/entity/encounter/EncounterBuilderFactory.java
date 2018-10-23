package com.tompy.entity.encounter;

import com.tompy.adventure.Adventure;
import com.tompy.player.Player;

public interface EncounterBuilderFactory {
    EncounterBuilder createEncounterBuilder(Player player, Adventure adventure);

    void addEncounter(Encounter encounter);
}
