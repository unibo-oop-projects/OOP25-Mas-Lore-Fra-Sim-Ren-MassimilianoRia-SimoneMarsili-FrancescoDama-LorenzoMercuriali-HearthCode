package it.unibo.oop.hearthcode.model.ai.evaluation.api;

import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;

/**
 * Evaluates a simulated game state from the AI point of view.
 */
@FunctionalInterface
public interface AiStateEvaluator {

    /**
     * Computes a score for the given simulated game state.
     *
     * @param gameState the simulated game state
     * @return the score
     */
    int evaluate(AiGameState gameState);

}
