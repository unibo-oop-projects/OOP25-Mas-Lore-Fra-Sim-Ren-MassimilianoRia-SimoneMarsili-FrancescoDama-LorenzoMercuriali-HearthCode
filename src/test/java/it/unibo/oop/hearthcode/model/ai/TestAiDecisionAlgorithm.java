package it.unibo.oop.hearthcode.model.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.AiActionGeneratorImpl;
import it.unibo.oop.hearthcode.model.ai.action.impl.AttackHeroAction;
import it.unibo.oop.hearthcode.model.ai.algorithm.api.AiDecisionAlgorithm;
import it.unibo.oop.hearthcode.model.ai.algorithm.impl.DepthLimitedLookaheadAiAlgorithm;
import it.unibo.oop.hearthcode.model.ai.algorithm.impl.GreedySequentialAiAlgorithm;
import it.unibo.oop.hearthcode.model.ai.evaluation.impl.HeuristicAiStateEvaluator;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.ai.transition.impl.AiStateTransitionImpl;

final class TestAiDecisionAlgorithm {

    @Test
    void testGreedyFindsLethalAttack() {
        final CardState attacker = AiTestSupport.card(1, 1, 5, 5, true);
        final AiDecisionAlgorithm algorithm = new GreedySequentialAiAlgorithm(
            new AiActionGeneratorImpl(),
            new AiStateTransitionImpl(),
            new HeuristicAiStateEvaluator()
        );

        final List<AiAction> actions = algorithm.decide(
            AiTestSupport.state(30, 0, List.of(), List.of(attacker), 5, List.of())
        );

        assertEquals(List.of(new AttackHeroAction(attacker.getCardId())), actions);
    }

    @Test
    void testLookaheadFindsTwoAttackLethal() {
        final CardState firstAttacker = AiTestSupport.card(1, 1, 4, 5, true);
        final CardState secondAttacker = AiTestSupport.card(2, 1, 4, 5, true);
        final AiDecisionAlgorithm algorithm = new DepthLimitedLookaheadAiAlgorithm(
            new AiActionGeneratorImpl(),
            new AiStateTransitionImpl(),
            new HeuristicAiStateEvaluator(),
            2
        );

        final List<AiAction> actions = algorithm.decide(
            AiTestSupport.state(30, 0, List.of(), List.of(firstAttacker, secondAttacker), 8, List.of())
        );

        assertEquals(2, actions.size());
        assertTrue(actions.contains(new AttackHeroAction(firstAttacker.getCardId())));
        assertTrue(actions.contains(new AttackHeroAction(secondAttacker.getCardId())));
    }

    @Test
    void testInvalidLookaheadDepth() {
        assertThrows(IllegalArgumentException.class, () -> new DepthLimitedLookaheadAiAlgorithm(
            new AiActionGeneratorImpl(),
            new AiStateTransitionImpl(),
            new HeuristicAiStateEvaluator(),
            0
        ));
    }
}
