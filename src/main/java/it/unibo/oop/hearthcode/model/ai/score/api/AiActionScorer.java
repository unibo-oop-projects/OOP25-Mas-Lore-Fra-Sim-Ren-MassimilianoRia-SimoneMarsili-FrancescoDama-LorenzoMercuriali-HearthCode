package it.unibo.oop.hearthcode.model.ai.score.api;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;

/**
 * Scores an AI action in a given simulated game state.
 */
@FunctionalInterface
public interface AiActionScorer {

    /**
     * Computes a heuristic score for the given action in the given state.
     *
     * @param gameState the current simulated state
     * @param action the action to evaluate
     * @return the heuristic score
     */
    int score(AiGameState gameState, AiAction action);

}
