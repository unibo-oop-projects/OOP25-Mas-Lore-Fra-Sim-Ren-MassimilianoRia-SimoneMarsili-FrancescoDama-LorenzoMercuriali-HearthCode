package it.unibo.oop.hearthcode.model.ai.api;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Snapshot of the game state used by the AI.
 */
public interface AiGameState {

    /**
     * @param playerId the identifier of the player
     * @return the player state associated with the given identifier
     */
    PlayerState getPlayerState(PlayerId playerId);

}
