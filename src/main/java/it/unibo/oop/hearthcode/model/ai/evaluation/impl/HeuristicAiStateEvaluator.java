package it.unibo.oop.hearthcode.model.ai.evaluation.impl;

import java.util.List;

import it.unibo.oop.hearthcode.model.ai.evaluation.api.AiStateEvaluator;
import it.unibo.oop.hearthcode.model.ai.evaluation.api.EvaluationResult;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.ai.simulation.api.PlayerState;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Heuristic evaluator for simulated AI game states.
 */
public final class HeuristicAiStateEvaluator implements AiStateEvaluator {

    private static final int HERO_HEALTH_WEIGHT = 18;
    private static final int HUMAN_LOW_HEALTH_PRESSURE_WEIGHT = 6;

    private static final int ARMY_SIZE_WEIGHT = 22;
    private static final int CARD_ATTACK_WEIGHT = 5;
    private static final int CARD_HEALTH_WEIGHT = 3;
    private static final int CARD_COST_WEIGHT = 2;

    private static final int HUMAN_ARMY_THREAT_WEIGHT = 7;
    private static final int HUMAN_ARMY_SIZE_DANGER_WEIGHT = 18;

    private static final int AI_REMAINING_MANA_PENALTY = 3;

    private static final int LOW_HEALTH_THRESHOLD = 2;
    private static final int LOW_HEALTH_ENEMY_PENALTY = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    public EvaluationResult evaluate(final AiGameState gameState) {
        final PlayerState ai = gameState.getPlayerState(PlayerId.AI);
        final PlayerState human = gameState.getPlayerState(PlayerId.HUMAN);

        if (human.getPlayerHealth() <= 0) {
            return EvaluationResult.victory();
        }
        if (ai.getPlayerHealth() <= 0) {
            return EvaluationResult.defeat();
        }

        final int score = this.heroHealthScore(ai, human)
            + this.armyPresenceScore(ai, human)
            + this.armyQualityScore(ai, human)
            + this.enemyThreatScore(human)
            + this.enemyLowHealthScore(human)
            + this.remainingManaScore(ai);

        return EvaluationResult.continueWithScore(score);
    }

    private int heroHealthScore(final PlayerState ai, final PlayerState human) {
        final int healthAdvantage = ai.getPlayerHealth() - human.getPlayerHealth();
        final int humanPressure = 30 - human.getPlayerHealth();
        return healthAdvantage * HERO_HEALTH_WEIGHT
            + humanPressure * HUMAN_LOW_HEALTH_PRESSURE_WEIGHT;
    }

    private int armyPresenceScore(final PlayerState ai, final PlayerState human) {
        final int aiArmySize = ai.getPlayerArmy().size();
        final int humanArmySize = human.getPlayerArmy().size();
        return (aiArmySize - humanArmySize) * ARMY_SIZE_WEIGHT
            - humanArmySize * HUMAN_ARMY_SIZE_DANGER_WEIGHT;
    }

    private int armyQualityScore(final PlayerState ai, final PlayerState human) {
        return this.armyValue(ai.getPlayerArmy()) - this.armyValue(human.getPlayerArmy());
    }

    private int enemyThreatScore(final PlayerState human) {
        return -this.totalAttack(human.getPlayerArmy()) * HUMAN_ARMY_THREAT_WEIGHT;
    }

    private int enemyLowHealthScore(final PlayerState human) {
        return -this.lowHealthCards(human.getPlayerArmy()) * LOW_HEALTH_ENEMY_PENALTY;
    }

    private int remainingManaScore(final PlayerState ai) {
        return -ai.getPlayerActualMana() * AI_REMAINING_MANA_PENALTY;
    }

    private int armyValue(final List<CardState> army) {
        return army.stream()
            .mapToInt(this::cardValue)
            .sum();
    }

    private int cardValue(final CardState card) {
        return card.getAttackValue() * CARD_ATTACK_WEIGHT
            + card.getHealth() * CARD_HEALTH_WEIGHT
            + card.getManaCost() * CARD_COST_WEIGHT;
    }

    private int totalAttack(final List<CardState> army) {
        return army.stream()
            .mapToInt(CardState::getAttackValue)
            .sum();
    }

    private int lowHealthCards(final List<CardState> army) {
        return (int) army.stream()
            .filter(card -> card.getHealth() <= LOW_HEALTH_THRESHOLD)
            .count();
    }

}
