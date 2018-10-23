package com.tompy.state;

import com.tompy.common.Builder;
import com.tompy.entity.encounter.Encounter;

public interface AdventureStateEncounterBuilder extends Builder<AdventureState> {
    AdventureStateEncounterBuilder encounter(Encounter encounter);
}
