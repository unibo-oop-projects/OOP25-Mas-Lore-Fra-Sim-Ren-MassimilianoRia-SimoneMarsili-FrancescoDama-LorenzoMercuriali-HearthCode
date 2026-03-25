package it.unibo.oop.hearthcode.model.ai.api;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Snapshot of the game state used by the AI.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface AiGameState {

    /**
     * @param playerId the identifier of the player
     * @return the player state associated with the given identifier
     */
    PlayerState getPlayerState(PlayerId playerId);

}
