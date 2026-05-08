package it.unibo.oop.hearthcode.model.ai;

import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.ai.simulation.impl.AiGameStateImpl;
import it.unibo.oop.hearthcode.model.creature.impl.CardStateImpl;
import it.unibo.oop.hearthcode.model.ai.simulation.impl.PlayerStateImpl;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardType;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

final class AiTestSupport {

    private AiTestSupport() {
    }

    static CardId id(final int id) {
        return new CardId(CardType.CREATURE, id);
    }

    static CardState card(
        final int id,
        final int manaCost,
        final int attackValue,
        final int health,
        final boolean usable
    ) {
        return new CardStateImpl(
            id(id),
            manaCost,
            attackValue,
            health,
            usable
        );
    }

    static AiGameState state(
        final int aiHealth,
        final int aiMana,
        final List<CardState> aiHand,
        final List<CardState> aiArmy,
        final int humanHealth,
        final List<CardState> humanArmy
    ) {
        return new AiGameStateImpl(
            new PlayerStateImpl(
                PlayerId.HUMAN,
                humanHealth,
                0,
                Optional.empty(),
                humanArmy
            ),
            new PlayerStateImpl(
                PlayerId.AI,
                aiHealth,
                aiMana,
                Optional.of(aiHand),
                aiArmy
            )
        );
    }

}
