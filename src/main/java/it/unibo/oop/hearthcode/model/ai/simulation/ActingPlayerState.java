package it.unibo.oop.hearthcode.model.ai.simulation;

import it.unibo.oop.hearthcode.model.player.api.Player;

/**
 * Snapshot representation of the acting {@link Player} state at a specific moment used in the AI decision simulation layer.
 * Implementations should be immutable.
 */
public interface ActingPlayerState extends PlayerState {

    /**
     * @return the hand state of the acting player
     */
    HandState getHand();

}
