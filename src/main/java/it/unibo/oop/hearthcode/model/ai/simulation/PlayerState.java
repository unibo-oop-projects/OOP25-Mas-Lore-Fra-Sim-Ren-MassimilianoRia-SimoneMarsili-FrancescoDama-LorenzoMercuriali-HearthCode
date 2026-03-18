package it.unibo.oop.hearthcode.model.ai.simulation;

import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Snapshot representation of the visible state of a {@link Player} at a specific moment used in the AI decision simulation layer.
 * Implementations should be immutable.
 */
public interface PlayerState {

    /**
     * @return the player id
     */
    PlayerId getId();

    /**
     * @return the actual amount of player's Mana
     */
    int getActualMana();

    /**
     * @return the amount of maximum Mana available for the turn
     */
    int getTurnManaLimit();

    /**
     * @return the amount of player's health
     */
    int getHealth();

}
