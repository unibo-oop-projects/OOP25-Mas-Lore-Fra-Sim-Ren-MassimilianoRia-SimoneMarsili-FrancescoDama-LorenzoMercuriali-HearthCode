package it.unibo.oop.hearthcode.model.ai.algorithm.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.api.AiActionGenerator;
import it.unibo.oop.hearthcode.model.ai.algorithm.api.AiDecisionAlgorithm;
import it.unibo.oop.hearthcode.model.ai.evaluation.api.AiStateEvaluator;
import it.unibo.oop.hearthcode.model.ai.evaluation.api.EvaluationResult;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.ai.transition.api.AiStateTransition;

/**
 * Depth-limited lookahead AI algorithm.
 * It explores legal action sequences up to a bounded depth and chooses the
 * sequence with the best heuristic evaluation.
 */
public final class DepthLimitedLookaheadAiAlgorithm implements AiDecisionAlgorithm {

    private static final int VICTORY_RANK = 1;
    private static final int CONTINUE_RANK = 0;
    private static final int DEFEAT_RANK = -1;

    private final AiActionGenerator actionGenerator;
    private final AiStateTransition stateTransition;
    private final AiStateEvaluator stateEvaluator;
    private final int maxDepth;

    /**
     * Creates a depth-limited lookahead algorithm.
     *
     * @param actionGenerator the component that generates legal actions
     * @param stateTransition the component that applies actions to simulated states
     * @param stateEvaluator the component that evaluates simulated states
     * @param maxDepth the maximum number of actions to explore
     */
    public DepthLimitedLookaheadAiAlgorithm(
        final AiActionGenerator actionGenerator,
        final AiStateTransition stateTransition,
        final AiStateEvaluator stateEvaluator,
        final int maxDepth
    ) {
        if (maxDepth <= 0) {
            throw new IllegalArgumentException("Maximum search depth must be greater than zero.");
        }
        this.actionGenerator = Objects.requireNonNull(actionGenerator);
        this.stateTransition = Objects.requireNonNull(stateTransition);
        this.stateEvaluator = Objects.requireNonNull(stateEvaluator);
        this.maxDepth = maxDepth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AiAction> decide(final AiGameState initialState) {
        Objects.requireNonNull(initialState);
        return List.copyOf(this.search(initialState.copy(), this.maxDepth).actions());
    }

    private SearchResult search(final AiGameState currentState, final int remainingDepth) {
        final EvaluationResult currentEvaluation = this.stateEvaluator.evaluate(currentState);
        if (remainingDepth == 0 || !currentEvaluation.isContinue()) {
            return new SearchResult(List.of(), currentState, currentEvaluation);
        }

        return this.actionGenerator.generateLegalActions(currentState).stream()
            .map(action -> this.searchAfter(currentState, remainingDepth, action))
            .max(Comparator.comparing(SearchResult::evaluation, this::compareEvaluations))
            .orElseGet(() -> new SearchResult(List.of(), currentState, currentEvaluation));
    }

    private SearchResult searchAfter(
        final AiGameState currentState,
        final int remainingDepth,
        final AiAction action
    ) {
        return this.prepend(
            action,
            this.search(this.stateTransition.apply(currentState, action), remainingDepth - 1)
        );
    }

    private SearchResult prepend(final AiAction action, final SearchResult result) {
        return new SearchResult(
            Stream.concat(Stream.of(action), result.actions().stream()).toList(),
            result.resultingState(),
            result.evaluation()
        );
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
