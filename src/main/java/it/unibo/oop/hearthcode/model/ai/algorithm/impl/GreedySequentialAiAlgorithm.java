package it.unibo.oop.hearthcode.model.ai.algorithm.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.api.AiActionGenerator;
import it.unibo.oop.hearthcode.model.ai.algorithm.api.AiDecisionAlgorithm;
import it.unibo.oop.hearthcode.model.ai.evaluation.api.AiStateEvaluator;
import it.unibo.oop.hearthcode.model.ai.evaluation.impl.EvaluationResult;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.ai.transition.api.AiStateTransition;

/**
 * Greedy sequential AI algorithm.
 */
public final class GreedySequentialAiAlgorithm implements AiDecisionAlgorithm {

    private static final int VICTORY_RANK = 1;
    private static final int CONTINUE_RANK = 0;
    private static final int DEFEAT_RANK = -1;

    private final AiActionGenerator actionGenerator;
    private final AiStateTransition stateTransition;
    private final AiStateEvaluator stateEvaluator;

    /**
     * Creates a greedy sequential algorithm.
     *
     * @param actionGenerator the component that generates legal actions
     * @param stateTransition the component that applies actions to simulated states
     * @param stateEvaluator the component that evaluates simulated states
     */
    public GreedySequentialAiAlgorithm(
        final AiActionGenerator actionGenerator,
        final AiStateTransition stateTransition,
        final AiStateEvaluator stateEvaluator
    ) {
        this.actionGenerator = Objects.requireNonNull(actionGenerator);
        this.stateTransition = Objects.requireNonNull(stateTransition);
        this.stateEvaluator = Objects.requireNonNull(stateEvaluator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AiAction> decide(final AiGameState initialState) {
        Objects.requireNonNull(initialState);
        final List<AiAction> chosenActions = new ArrayList<>();
        AiGameState currentState = initialState.copy();
    
        while (true) {
            final EvaluationResult currentEvaluation = this.stateEvaluator.evaluate(currentState);
            if (!currentEvaluation.isContinue()) {
                return List.copyOf(chosenActions);
            }

            final Optional<ScoredAction> bestAction = this.bestImprovingAction(currentState, currentEvaluation);
            if (bestAction.isEmpty()) {
                return List.copyOf(chosenActions);
            }

            final AiAction selectedAction = bestAction.get().action();
            chosenActions.add(selectedAction);
            currentState = bestAction.get().resultingState();
        }
    }

    private Optional<ScoredAction> bestImprovingAction(
        final AiGameState currentState,
        final EvaluationResult currentEvaluation
    ) {
        return this.actionGenerator.generateLegalActions(currentState).stream()
            .map(action -> this.scoreAction(currentState, action))
            .filter(scoredAction -> this.isBetter(scoredAction.evaluation(), currentEvaluation))
            .max(Comparator.comparing(ScoredAction::evaluation, this::compareEvaluations));
    }

    private ScoredAction scoreAction(final AiGameState currentState, final AiAction action) {
        final AiGameState resultingState = this.stateTransition.apply(currentState, action);
        final EvaluationResult evaluation = this.stateEvaluator.evaluate(resultingState);
        return new ScoredAction(action, resultingState, evaluation);
    }

    private boolean isBetter(final EvaluationResult candidate, final EvaluationResult baseline) {
        return this.compareEvaluations(candidate, baseline) > 0;
    }

    private int compareEvaluations(final EvaluationResult first, final EvaluationResult second) {
        final int rankComparison = Integer.compare(this.rank(first), this.rank(second));
        if (rankComparison != 0) {
            return rankComparison;
        }
        return Integer.compare(first.score(), second.score());
    }

    private int rank(final EvaluationResult evaluation) {
        if (evaluation.isVictory()) {
            return VICTORY_RANK;
        }
        if (evaluation.isDefeat()) {
            return DEFEAT_RANK;
        }
        return CONTINUE_RANK;
    }

}
