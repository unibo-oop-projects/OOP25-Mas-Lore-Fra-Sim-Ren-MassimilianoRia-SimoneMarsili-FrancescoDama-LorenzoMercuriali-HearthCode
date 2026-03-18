package it.unibo.oop.hearthcode.model.ai.simulation;

import it.unibo.oop.hearthcode.model.player.api.Player;

/**
 * Snapshot representation of the opponent {@link Player} state at a specific moment used in the AI decision simulation layer.
 * Implementations should be immutable.
 */
public interface OpponentPlayerState extends PlayerState {

    /**
     * @return the number of cards in the opponent's hand
     */
    int getHandSize();

}
