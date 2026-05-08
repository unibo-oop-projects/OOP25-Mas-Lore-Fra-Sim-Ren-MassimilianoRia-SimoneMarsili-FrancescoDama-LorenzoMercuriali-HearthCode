package it.unibo.oop.hearthcode.model.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

final class TestAiSimulation {

    private static final CardId HAND_CARD_ID = AiTestSupport.id(1);
    private static final CardId ARMY_CARD_ID = AiTestSupport.id(2);
    private static final int HERO_HEALTH = 30;
    private static final int CARD_FULL_HEALTH = 5;
    private static final int CARD_DAMAGED_HEALTH = 3;
    private static final int COPY_DAMAGE = 5;
    private static final int HUMAN_DAMAGED_HEALTH = 25;

    @Test
    void testCardState() {
        final CardState card = AiTestSupport.card(1, 1, 2, 3, true);

        card.damage(2);
        assertEquals(1, card.getHealth());
        card.damage(CARD_FULL_HEALTH);
        assertTrue(card.isDead());
        card.awaken();
        assertTrue(card.isUsable());
        card.exhaust();
        assertFalse(card.isUsable());
        assertThrows(IllegalArgumentException.class, () -> card.damage(-1));
    }

    @Test
    void testStateCopy() {
        final AiGameState state = AiTestSupport.state(
            HERO_HEALTH,
            CARD_FULL_HEALTH,
            List.of(AiTestSupport.card(1, 2, 3, 4, true)),
            List.of(AiTestSupport.card(2, 1, 2, CARD_FULL_HEALTH, true)),
            HERO_HEALTH,
            List.of()
        );

        final AiGameState copy = state.copy();
        copy.damagePlayer(PlayerId.HUMAN, COPY_DAMAGE);
        copy.damageCard(PlayerId.AI, ARMY_CARD_ID, 2);
        copy.placeCard(PlayerId.AI, HAND_CARD_ID);

        assertEquals(HERO_HEALTH, state.getPlayerState(PlayerId.HUMAN).getPlayerHealth());
        assertEquals(CARD_FULL_HEALTH, state.getArmyCard(PlayerId.AI, ARMY_CARD_ID).get().getHealth());
        assertTrue(state.getHandCard(PlayerId.AI, HAND_CARD_ID).isPresent());
        assertEquals(HUMAN_DAMAGED_HEALTH, copy.getPlayerState(PlayerId.HUMAN).getPlayerHealth());
        assertEquals(CARD_DAMAGED_HEALTH, copy.getArmyCard(PlayerId.AI, ARMY_CARD_ID).get().getHealth());
        assertTrue(copy.getHandCard(PlayerId.AI, HAND_CARD_ID).isEmpty());
    }
}
