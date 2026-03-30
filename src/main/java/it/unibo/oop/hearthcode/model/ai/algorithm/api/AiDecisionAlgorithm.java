package it.unibo.oop.hearthcode.model.ai.algorithm.api;

import java.util.List;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;

/**
 * Represents an AI decision algorithm that computes a turn plan.
 */
@FunctionalInterface
public interface AiDecisionAlgorithm {

    /**
     * Computes the sequence of actions the AI wants to perform for the current turn.
     *
     * @param initialState the initial simulated state
     * @return the ordered list of chosen actions
     */
    List<AiAction> decide(AiGameState initialState);

}
