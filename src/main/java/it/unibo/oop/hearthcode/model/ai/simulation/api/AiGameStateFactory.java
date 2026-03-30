package it.unibo.oop.hearthcode.model.ai.simulation.api;

import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;

/**
 * Factory for creating AI game states starting from a board game instance.
 */
@FunctionalInterface
public interface AiGameStateFactory {

    /**
     * Creates a mutable AI game state starting from the current board game state.
     *
     * @param game the current board game
     * @return the AI game state representing the current turn state
     */
    AiGameState create(BoardGame game);

}
