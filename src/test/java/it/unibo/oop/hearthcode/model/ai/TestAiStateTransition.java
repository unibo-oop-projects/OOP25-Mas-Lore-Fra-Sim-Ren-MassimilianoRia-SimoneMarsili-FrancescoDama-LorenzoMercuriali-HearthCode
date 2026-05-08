package it.unibo.oop.hearthcode.model.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.ai.action.impl.AttackCardAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.AttackHeroAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.PlayCardAction;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.ai.transition.api.AiStateTransition;
import it.unibo.oop.hearthcode.model.ai.transition.impl.AiStateTransitionImpl;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

final class TestAiStateTransition {

    private static final CardId CARD_ID = AiTestSupport.id(1);
    private static final int DAMAGED_HUMAN_HEALTH = 6;

    private AiStateTransition transition;

    @BeforeEach
    void initTest() {
        this.transition = new AiStateTransitionImpl();
    }

    @Test
    void testPlayCard() {
        final AiGameState state = AiTestSupport.state(
            30,
            4,
            List.of(AiTestSupport.card(1, 2, 3, 2, true)),
            List.of(),
            30,
            List.of()
        );

        final AiGameState next = this.transition.apply(state, new PlayCardAction(CARD_ID));

        assertEquals(4, state.getPlayerState(PlayerId.AI).getPlayerActualMana());
        assertEquals(2, next.getPlayerState(PlayerId.AI).getPlayerActualMana());
        assertTrue(next.getHandCard(PlayerId.AI, CARD_ID).isEmpty());
        assertFalse(next.getArmyCard(PlayerId.AI, CARD_ID).get().isUsable());
    }

    @Test
    void testAttackHero() {
        final CardState attacker = AiTestSupport.card(1, 1, 4, 5, true);
        final AiGameState state = AiTestSupport.state(
            30,
            0,
            List.of(),
            List.of(attacker),
            10,
            List.of()
        );

        final AiGameState next = this.transition.apply(state, new AttackHeroAction(attacker.getCardId()));

        assertEquals(DAMAGED_HUMAN_HEALTH, next.getPlayerState(PlayerId.HUMAN).getPlayerHealth());
        assertFalse(next.getArmyCard(PlayerId.AI, attacker.getCardId()).get().isUsable());
    }

    @Test
    void testAttackCard() {
        final CardState attacker = AiTestSupport.card(1, 1, 3, 4, true);
        final CardState defender = AiTestSupport.card(2, 1, 2, 3, true);
        final AiGameState state = AiTestSupport.state(
            30,
            0,
            List.of(),
            List.of(attacker),
            30,
            List.of(defender)
        );

        final AiGameState next = this.transition.apply(
            state,
            new AttackCardAction(attacker.getCardId(), defender.getCardId())
        );

        assertTrue(next.getArmyCard(PlayerId.HUMAN, defender.getCardId()).isEmpty());
        assertEquals(2, next.getArmyCard(PlayerId.AI, attacker.getCardId()).get().getHealth());
        assertFalse(next.getArmyCard(PlayerId.AI, attacker.getCardId()).get().isUsable());
    }
}
