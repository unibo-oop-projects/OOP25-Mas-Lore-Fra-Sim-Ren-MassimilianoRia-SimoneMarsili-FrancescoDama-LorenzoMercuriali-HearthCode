package it.unibo.oop.hearthcode.model.ai.evaluation.api;

/**
 * Result of an AI state evaluation.
 *
 * @param type the evaluation result type
 * @param score the heuristic score associated with the state
 */
public record EvaluationResult(EvaluationResultType type, int score) {

    /**
     * Creates a winning evaluation result.
     *
     * @return a winning evaluation result
     */
    public static EvaluationResult victory() {
        return new EvaluationResult(EvaluationResultType.VICTORY, 0);
    }

    /**
     * Creates a losing evaluation result.
     *
     * @return a losing evaluation result
     */
    public static EvaluationResult defeat() {
        return new EvaluationResult(EvaluationResultType.DEFEAT, 0);
    }

    /**
     * Creates a non-terminal evaluation result.
     *
     * @param score the heuristic score
     * @return a continuing evaluation result
     */
    public static EvaluationResult continueWithScore(final int score) {
        return new EvaluationResult(EvaluationResultType.CONTINUE, score);
    }

    /**
     * Checks whether this evaluation represents a winning state.
     *
     * @return true if this is a winning evaluation
     */
    public boolean isVictory() {
        return this.type == EvaluationResultType.VICTORY;
    }

    /**
     * Checks whether this evaluation represents a losing state.
     *
     * @return true if this is a losing evaluation
     */
    public boolean isDefeat() {
        return this.type == EvaluationResultType.DEFEAT;
    }

    /**
     * Checks whether this evaluation represents a non-terminal state.
     *
     * @return true if this is a continuing evaluation
     */
    public boolean isContinue() {
        return this.type == EvaluationResultType.CONTINUE;
    }

}
