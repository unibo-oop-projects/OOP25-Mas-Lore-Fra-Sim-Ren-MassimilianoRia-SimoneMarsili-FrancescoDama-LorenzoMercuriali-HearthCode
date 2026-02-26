package it.unibo.oop.hearthcode.model.deck.api;

/**
 * A specific type of card that can be deployed during the match.
 */
public interface Creature extends Card {

    /**
     * @return the health of the creature
     */
    Integer getHealth();

    /**
     * @return the value attack of the creature
     */
    Integer getAttackValue();

    /**
     * Decrease the creature's health.
     * 
     * @param damage the amount of damage inflicted
     * @return the updated value of the creature's health
     */
    Integer decreaseHealth(Integer damage);

}
