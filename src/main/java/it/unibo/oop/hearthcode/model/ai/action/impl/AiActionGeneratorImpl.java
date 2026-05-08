package it.unibo.oop.hearthcode.model.ai.action.impl;

import java.util.List;
import java.util.stream.Stream;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.api.AiActionGenerator;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.ai.simulation.api.PlayerState;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Implementation of {@link AiActionGenerator}.
 */
public final class AiActionGeneratorImpl implements AiActionGenerator {

    private static final int ARMY_MAX_SIZE = 5;

    @Override
    public List<AiAction> generateLegalActions(final AiGameState gameState) {
        final PlayerState aiPlayer = gameState.getPlayerState(PlayerId.AI);
        final PlayerState humanPlayer = gameState.getPlayerState(PlayerId.HUMAN);
        return Stream.concat(
            this.playableCardActions(aiPlayer),
            this.attackActions(aiPlayer, humanPlayer)
        ).toList();
    }

    private Stream<AiAction> playableCardActions(final PlayerState aiPlayer) {
        return aiPlayer.getPlayerArmy().size() >= ARMY_MAX_SIZE
            ? Stream.empty()
            : aiPlayer.getPlayerHand().stream()
                .flatMap(List::stream)
                .filter(card -> card.getManaCost() <= aiPlayer.getPlayerActualMana())
                .map(card -> new PlayCardAction(card.getCardId()));
    }

    private Stream<AiAction> attackActions(final PlayerState aiPlayer, final PlayerState humanPlayer) {
        final List<CardState> humanArmy = humanPlayer.getPlayerArmy();
        return aiPlayer.getPlayerArmy().stream()
            .filter(CardState::isUsable)
            .flatMap(attacker -> Stream.concat(
                Stream.of(new AttackHeroAction(attacker.getCardId())),
                humanArmy.stream()
                    .map(defender -> new AttackCardAction(attacker.getCardId(), defender.getCardId()))
            ));
    }

}
