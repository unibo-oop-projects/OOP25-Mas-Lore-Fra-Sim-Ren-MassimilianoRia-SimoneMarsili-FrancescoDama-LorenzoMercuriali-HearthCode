package it.unibo.oop.hearthcode.model.ai.simulation.impl;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameStateFactory;
import it.unibo.oop.hearthcode.model.ai.simulation.api.PlayerState;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Implementation of {@link AiGameStateFactory}.
 */
public class AiGameStateFactoryImpl implements AiGameStateFactory {

    private static final PlayerId HUMAN_PLAYER = PlayerId.HUMAN;
    private static final PlayerId AI_PLAYER = PlayerId.AI;

    /**
     * {@inheritDoc}
     */
    @Override
    public AiGameState create(final BoardGame game) {
        final PlayerState humanPlayerState = this.mapHumanPlayer(game, HUMAN_PLAYER);
        final PlayerState aiPlayerState = this.mapAiPlayer(game, AI_PLAYER);
        return new AiGameStateImpl(humanPlayerState, aiPlayerState);
    }

    private PlayerState mapAiPlayer(final BoardGame game, final PlayerId aiPlayer) {
        return new PlayerStateImpl(
            aiPlayer,
            game.getPlayerHealth(aiPlayer),
            game.getPlayerActualMana(aiPlayer),
            Optional.of(game.getHandCardsCopies(aiPlayer)),
            game.getArmyCardsCopies(aiPlayer)
        );
    }

    private PlayerState mapHumanPlayer(final BoardGame game, final PlayerId humanPlayer) {
        return new PlayerStateImpl(
            humanPlayer,
            game.getPlayerHealth(humanPlayer),
            game.getPlayerActualMana(humanPlayer),
            Optional.empty(),
            game.getArmyCardsCopies(humanPlayer)
        );
    }

}
