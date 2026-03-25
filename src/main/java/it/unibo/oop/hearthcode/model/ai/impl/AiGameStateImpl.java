package it.unibo.oop.hearthcode.model.ai.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.oop.hearthcode.model.ai.api.AiGameState;
import it.unibo.oop.hearthcode.model.ai.api.PlayerState;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Implementation of {@link AiGameState}.
 */
public class AiGameStateImpl implements AiGameState {

    private final Map<PlayerId, PlayerState> players = new HashMap<>();

    /**
     * Creates an AI game state containing the two player states.
     *
     * @param humanPlayerState the state of the human player
     * @param aiPlayerState the state of the AI player
     */
    public AiGameStateImpl(PlayerState humanPlayerState, PlayerState aiPlayerState) {
        this.players.put(humanPlayerState.getPlayerId(), humanPlayerState);
        this.players.put(aiPlayerState.getPlayerId(), aiPlayerState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerState getPlayerState(final PlayerId playerId) {
        return this.players.get(playerId);
    }

}
