package it.unibo.oop.hearthcode.model.ai.algorithm.impl;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.evaluation.impl.EvaluationResult;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;

/**
 * Represents an action along with the resulting simulated state and its evaluation score.
 */
public record ScoredAction(
    AiAction action,
    AiGameState resultingState,
    EvaluationResult evaluation
) { }
