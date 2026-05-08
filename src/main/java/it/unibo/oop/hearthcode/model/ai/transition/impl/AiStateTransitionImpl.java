package it.unibo.oop.hearthcode.model.ai.transition.impl;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.AttackCardAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.AttackHeroAction;
import it.unibo.oop.hearthcode.model.ai.action.impl.PlayCardAction;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.ai.transition.api.AiStateTransition;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Implementation of {@link AiStateTransition}.
 */
public final class AiStateTransitionImpl implements AiStateTransition {

    private static final PlayerId HUMAN_PLAYER = PlayerId.HUMAN;
    private static final PlayerId AI_PLAYER = PlayerId.AI;

    @Override
    public AiGameState apply(final AiGameState gameState, final AiAction action) {
        final AiGameState nextState = gameState.copy();
        if (action instanceof PlayCardAction playCardAction) {
            this.applyPlayCard(nextState, playCardAction);
        } else if (action instanceof AttackHeroAction attackHeroAction) {
            this.applyAttackHero(nextState, attackHeroAction);
        } else if (action instanceof AttackCardAction attackCardAction) {
            this.applyAttackCard(nextState, attackCardAction);
        }
        return nextState;
    }

    private void applyPlayCard(final AiGameState gameState, final PlayCardAction action) {
        final CardState card = gameState.getHandCard(AI_PLAYER, action.cardId())
            .orElseThrow(() -> new IllegalArgumentException("Card not found in AI hand: " + action.cardId()));
        if (card.getManaCost() > gameState.getPlayerState(AI_PLAYER).getPlayerActualMana()) {
            throw new IllegalStateException("Not enough mana to play card: " + action.cardId());
        }
        gameState.consumeMana(AI_PLAYER, card.getManaCost());
        gameState.placeCard(AI_PLAYER, action.cardId());
    }

    private void applyAttackHero(final AiGameState gameState, final AttackHeroAction action) {
        final CardState attacker = gameState.getArmyCard(AI_PLAYER, action.attackerId())
            .orElseThrow(() -> new IllegalArgumentException("Attacker not found in AI army: " + action.attackerId()));
        if (!attacker.isUsable()) {
            throw new IllegalStateException("Attacker is not usable: " + action.attackerId());
        }
        gameState.damagePlayer(HUMAN_PLAYER, attacker.getAttackValue());
        gameState.exhaustCard(AI_PLAYER, action.attackerId());
    }

    private void applyAttackCard(final AiGameState gameState, final AttackCardAction action) {
        final CardState attackerBefore = gameState.getArmyCard(AI_PLAYER, action.attackerId())
            .orElseThrow(() -> new IllegalArgumentException("Attacker not found in AI army: " + action.attackerId()));
        final CardState defenderBefore = gameState.getArmyCard(HUMAN_PLAYER, action.defenderId())
            .orElseThrow(() -> new IllegalArgumentException("Defender not found in human army: " + action.defenderId()));
        if (!attackerBefore.isUsable()) {
            throw new IllegalStateException("Attacker is not usable: " + action.attackerId());
        }
        final int attackerDamage = attackerBefore.getAttackValue();
        final int defenderDamage = defenderBefore.getAttackValue();
        gameState.damageCard(HUMAN_PLAYER, action.defenderId(), attackerDamage);
        gameState.damageCard(AI_PLAYER, action.attackerId(), defenderDamage);
        gameState.exhaustCard(AI_PLAYER, action.attackerId());
        this.removeDeadIfNecessary(gameState, HUMAN_PLAYER, action.defenderId());
        this.removeDeadIfNecessary(gameState, AI_PLAYER, action.attackerId());
    }

    private void removeDeadIfNecessary(
        final AiGameState gameState,
        final PlayerId playerId,
        final CardId cardId
    ) {
        gameState.getArmyCard(playerId, cardId)
            .filter(card -> card.getHealth() <= 0)
            .ifPresent(card -> gameState.destroyCard(playerId, cardId));
    }

}
