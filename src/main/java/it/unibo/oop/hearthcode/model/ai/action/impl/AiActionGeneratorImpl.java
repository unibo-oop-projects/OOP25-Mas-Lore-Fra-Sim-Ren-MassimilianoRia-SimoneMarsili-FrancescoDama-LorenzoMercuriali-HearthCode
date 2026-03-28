package it.unibo.oop.hearthcode.model.ai.action.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.api.AiActionGenerator;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.ai.simulation.api.CardState;
import it.unibo.oop.hearthcode.model.ai.simulation.api.PlayerState;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;

/**
 * Implementation of {@link AiActionGenerator}.
 */
public class AiActionGeneratorImpl implements AiActionGenerator {

    @Override
    public List<AiAction> generateLegalActions(final AiGameState gameState) {
        final List<AiAction> actions = new ArrayList<>();
        final PlayerState aiPlayer = gameState.getPlayerState(new PlayerId(PlayerType.AI_PLAYER));
        final PlayerState humanPlayer = gameState.getPlayerState(new PlayerId(PlayerType.HUMAN_PLAYER));
        this.addPlayableCardActions(actions, aiPlayer);
        this.addAttackActions(actions, aiPlayer, humanPlayer);
        return List.copyOf(actions);
    }

    private void addPlayableCardActions(final List<AiAction> actions, final PlayerState aiPlayer) {
        aiPlayer.getPlayerHand().ifPresent(hand -> hand.stream()
            .filter(card -> card.getManaCost() <= aiPlayer.getPlayerActualMana())
            .map(card -> new PlayCardAction(card.getCardId()))
            .forEach(actions::add));
    }

    private void addAttackActions(
        final List<AiAction> actions,
        final PlayerState aiPlayer,
        final PlayerState humanPlayer
    ) {
        final List<CardState> humanArmy = humanPlayer.getPlayerArmy();
        aiPlayer.getPlayerArmy().stream()
            .filter(CardState::isUsable)
            .forEach(attacker -> {
                actions.add(new AttackHeroAction(attacker.getCardId()));
                humanArmy.stream()
                    .map(defender -> new AttackCardAction(attacker.getCardId(), defender.getCardId()))
                    .forEach(actions::add);
            });
    }

}
