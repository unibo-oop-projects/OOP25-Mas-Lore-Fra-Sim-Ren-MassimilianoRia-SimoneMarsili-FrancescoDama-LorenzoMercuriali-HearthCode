package it.unibo.oop.hearthcode.model.ai.simulation;

import it.unibo.oop.hearthcode.model.creature.api.Creature;

/**
 * Snapshot representation of the {@link Creature} state at a specific moment used in the AI decision simulation layer.
 * Implementations should be immutable.
 */
public interface CreatureState extends CardState {

    /**
     * @return the health of the creature
     */
    int getHealth();
    
    /**
     * @return the attack value of the creature
     */
    int getAttackValue();

}
