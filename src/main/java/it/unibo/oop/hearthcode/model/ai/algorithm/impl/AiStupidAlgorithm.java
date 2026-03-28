package it.unibo.oop.hearthcode.model.ai.algorithm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.api.AiActionGenerator;
import it.unibo.oop.hearthcode.model.ai.algorithm.api.AiDecisionAlgorithm;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.ai.transition.api.AiStateTransition;

/**
 * Stupid (and temp) AI decision algorithm that always selects the first available action.
 */
public class AiStupidAlgorithm implements AiDecisionAlgorithm {

    private final AiActionGenerator actionGenerator;
    private final AiStateTransition stateTransition;

    /**
     * Creates the algorithm.
     *
     * @param actionGenerator the component that generates legal actions
     * @param stateTransition the component that applies actions to a simulated state
     */
    public AiStupidAlgorithm(
        final AiActionGenerator actionGenerator,
        final AiStateTransition stateTransition
    ) {
        this.actionGenerator = actionGenerator;
        this.stateTransition = stateTransition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AiAction> decide(final AiGameState initialState) {
        final List<AiAction> chosenActions = new ArrayList<>();
        AiGameState currentState = initialState;
        while (true) {
            final List<AiAction> availableActions = this.actionGenerator.generateLegalActions(currentState);
            if (availableActions.isEmpty()) {
                return List.copyOf(chosenActions);
            }
            final AiAction selectedAction = availableActions.get(0);
            chosenActions.add(selectedAction);
            currentState = this.stateTransition.apply(currentState, selectedAction);
        }
    }

}
