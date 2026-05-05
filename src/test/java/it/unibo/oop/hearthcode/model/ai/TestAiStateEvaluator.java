package it.unibo.oop.hearthcode.model.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.ai.evaluation.api.AiStateEvaluator;
import it.unibo.oop.hearthcode.model.ai.evaluation.impl.HeuristicAiStateEvaluator;

final class TestAiStateEvaluator {

    private static final int HERO_HEALTH = 30;
    private static final int EXPECTED_SCORE = 67;

    private AiStateEvaluator evaluator;

    @BeforeEach
    void initTest() {
        this.evaluator = new HeuristicAiStateEvaluator();
    }

    @Test
    void testTerminalStates() {
        assertTrue(this.evaluator.evaluate(
            AiTestSupport.state(HERO_HEALTH, 0, List.of(), List.of(), 0, List.of())
        ).isVictory());
        assertTrue(this.evaluator.evaluate(
            AiTestSupport.state(0, 0, List.of(), List.of(), HERO_HEALTH, List.of())
        ).isDefeat());
    }

    @Test
    void testContinueScore() {
        final var state = AiTestSupport.state(
            HERO_HEALTH,
            2,
            List.of(),
            List.of(AiTestSupport.card(1, 1, 3, 4, true)),
            26,
            List.of(AiTestSupport.card(2, 1, 2, 1, true))
        );
        final var result = this.evaluator.evaluate(state);

        assertTrue(result.isContinue());
        assertEquals(EXPECTED_SCORE, result.score());
    }
}
