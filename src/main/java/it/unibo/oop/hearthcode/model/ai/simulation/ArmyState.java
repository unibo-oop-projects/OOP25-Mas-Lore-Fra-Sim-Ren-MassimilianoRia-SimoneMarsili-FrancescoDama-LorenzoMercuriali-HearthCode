package it.unibo.oop.hearthcode.model.ai.simulation;

import java.util.List;

import it.unibo.oop.hearthcode.model.army.api.Army;

/**
 * Snapshot representation of the {@link Army} state at a specific moment used in the AI decision simulation layer.
 * Implementations should be immutable.
 */
public interface ArmyState {

    /**
     * @return an immutable list of awake creature states
     */
    List<CreatureState> getAwakeCreatures();

    /**
     * @return an immutable list of sleeping creature states
     */
    List<CreatureState> getSleepingCreatures();

    /**
     * @return whether the army is full
     */
    boolean isFull();

}
