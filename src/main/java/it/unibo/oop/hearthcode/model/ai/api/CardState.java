package it.unibo.oop.hearthcode.model.ai.api;

import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * Representation of a card state used by the AI.
 */
public interface CardState {

    /**
     * @return the card identifier
     */
    CardId getCardId();

    /**
     * @return the mana cost
     */
    int getManaCost();

    /**
     * @return the health value
     */
    int getHealth();

    /**
     * @return the attack value
     */
    int getAttackValue();

    /**
     * @return whether the card is usable
     */
    boolean isUsable();

}
