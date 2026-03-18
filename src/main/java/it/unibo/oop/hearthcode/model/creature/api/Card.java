package it.unibo.oop.hearthcode.model.creature.api;

/**
 * A card that can be played during the match.
 */
public interface Card {

    /**
     * @return the specific CardId of the instance of the card
     */
    CardId getId();

    /**
     * @return the name of the card
     */
    String getName();

    /**
     * @return the cost of the card in terms of mana
     */
    Integer getManaCost();

}
