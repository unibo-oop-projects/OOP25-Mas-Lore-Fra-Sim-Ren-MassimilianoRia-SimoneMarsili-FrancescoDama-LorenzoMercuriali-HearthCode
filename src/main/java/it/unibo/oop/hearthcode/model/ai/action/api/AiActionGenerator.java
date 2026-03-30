package it.unibo.oop.hearthcode.model.ai.action.api;

import java.util.List;

import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;

/**
 * Generates all legal actions available to the AI in a given game state.
 */
@FunctionalInterface
public interface AiActionGenerator {

    /**
     * Generates all legal actions currently available to the AI player.
     *
     * @param gameState the current simulated game state
     * @return the list of legal actions
     */
    List<AiAction> generateLegalActions(AiGameState gameState);

}
