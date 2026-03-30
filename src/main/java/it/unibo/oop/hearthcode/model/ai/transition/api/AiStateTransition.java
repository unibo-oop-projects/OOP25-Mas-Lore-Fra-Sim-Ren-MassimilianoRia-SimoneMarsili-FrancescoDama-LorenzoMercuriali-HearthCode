package it.unibo.oop.hearthcode.model.ai.transition.api;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;

/**
 * Applies an AI action to a simulated game state and returns the resulting state.
 */
@FunctionalInterface
public interface AiStateTransition {

    /**
     * Applies the given action to the given state and returns the resulting state.
     *
     * @param gameState the source simulated state
     * @param action the action to apply
     * @return the resulting simulated state
     */
    AiGameState apply(AiGameState gameState, AiAction action);

}
