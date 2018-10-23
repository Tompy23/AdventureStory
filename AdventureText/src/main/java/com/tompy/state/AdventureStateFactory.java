package com.tompy.state;

public interface AdventureStateFactory {

    AdventureState getExploreState();

    AdventureStateEncounterBuilder createEncounterState();

    AdventureStateCombatBuilder createCombatState();
}
