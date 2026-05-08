package it.unibo.oop.hearthcode.model.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.api.AiActionGenerator;
import it.unibo.oop.hearthcode.model.ai.action.impl.AiActionGeneratorImpl;
import it.unibo.oop.hearthcode.model.ai.action.impl.AttackCardAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.AttackHeroAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.PlayCardAction;
import it.unibo.oop.hearthcode.model.creature.api.CardState;

final class TestAiActionGenerator {

    private static final int AI_MANA = 2;
    private static final int FULL_ARMY_SIZE = 5;

    private AiActionGenerator generator;

    @BeforeEach
    void initTest() {
        this.generator = new AiActionGeneratorImpl();
    }

    @Test
    void testLegalActions() {
        final CardState playable = AiTestSupport.card(1, AI_MANA, 3, 2, true);
        final CardState expensive = AiTestSupport.card(2, 6, 8, 8, true);
        final CardState attacker = AiTestSupport.card(3, 1, 4, 2, true);
        final CardState exhausted = AiTestSupport.card(4, 1, 1, 2, false);
        final CardState defender = AiTestSupport.card(5, 1, 2, 3, true);
        final var state = AiTestSupport.state(
            30,
            AI_MANA,
            List.of(playable, expensive),
            List.of(attacker, exhausted),
            30,
            List.of(defender)
        );

        final List<AiAction> actions = this.generator.generateLegalActions(state);

        assertEquals(3, actions.size());
        assertTrue(actions.contains(new PlayCardAction(playable.getCardId())));
        assertFalse(actions.contains(new PlayCardAction(expensive.getCardId())));
        assertTrue(actions.contains(new AttackHeroAction(attacker.getCardId())));
        assertTrue(actions.contains(new AttackCardAction(attacker.getCardId(), defender.getCardId())));
        assertFalse(actions.contains(new AttackHeroAction(exhausted.getCardId())));
    }

    @Test
    void testFullArmySkipsPlayActions() {
        final List<CardState> army = IntStream.range(0, FULL_ARMY_SIZE)
            .mapToObj(n -> AiTestSupport.card(n, 1, 1, 1, false))
            .toList();
        final var state = AiTestSupport.state(
            30,
            AI_MANA,
            List.of(AiTestSupport.card(10, 1, 1, 1, true)),
            army,
            30,
            List.of()
        );

        assertTrue(this.generator.generateLegalActions(state).isEmpty());
    }
}
