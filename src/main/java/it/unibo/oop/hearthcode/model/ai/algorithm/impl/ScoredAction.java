package it.unibo.oop.hearthcode.model.ai.algorithm.impl;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.evaluation.api.EvaluationResult;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;

/**
 * Represents an action along with the resulting simulated state and its evaluation score.
 * 
 * @param action the AI action being evaluated
 * @param resultingState the simulated game state resulting from applying the action
 * @param evaluation the evaluation result of the resulting state
 */
public record ScoredAction(
    AiAction action,
    AiGameState resultingState,
    EvaluationResult evaluation
) { }
