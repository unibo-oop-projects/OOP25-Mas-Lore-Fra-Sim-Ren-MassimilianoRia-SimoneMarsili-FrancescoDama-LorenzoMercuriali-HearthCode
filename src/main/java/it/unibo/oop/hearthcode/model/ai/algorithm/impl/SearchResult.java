package it.unibo.oop.hearthcode.model.ai.algorithm.impl;

import java.util.List;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.evaluation.api.EvaluationResult;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;

/**
 * Represents the result of a search in the depth-limited lookahead algorithm.
 * 
 * @param actions the sequence of actions leading to the resulting state
 * @param resultingState the simulated game state resulting from applying the actions
 * @param evaluation the evaluation result of the resulting state
 */
public record SearchResult(
    List<AiAction> actions,
    AiGameState resultingState,
    EvaluationResult evaluation
) {

    /**
     * Builds a search result with an immutable action sequence.
     */
    public SearchResult {
        actions = List.copyOf(actions);
    }

}
