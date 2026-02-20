package it.unibo.oop.heartcode.model.deck.api;

/**
 * A card that can be played during the match.
 */
public interface Card {

    /**
     * @return the specific id of the istance of the card
     */
    Integer getId();

    /**
     * @return the name of the card
     */
    String getName();

    /**
     * @return the cost of the card in terms of mana
     */
    Integer getManaCost();

}
