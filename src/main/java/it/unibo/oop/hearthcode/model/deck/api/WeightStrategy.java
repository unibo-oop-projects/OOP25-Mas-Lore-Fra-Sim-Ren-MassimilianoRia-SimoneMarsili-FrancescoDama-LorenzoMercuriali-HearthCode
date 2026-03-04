package it.unibo.oop.hearthcode.model.deck.api;

import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;

/**
 * It modelizes a WeightStrategy for creature cards.
 * Different creature may have different weights in the game.
 */
@FunctionalInterface
public interface WeightStrategy {

    /**
     * @param definition the definition of the card to be evaluated
     * @return the weight of the specific card
     */
    int getWeight(CreatureDefinition definition);
}
