package it.unibo.oop.hearthcode.model.deck.api;

/**
 * It modelizes the set of the cards drawable by his own player.
 */
public interface Deck {

    /**
     * Represents the act of drawing a card from the deck.
     * 
     * @return the card drawed
     */
    Card draw();

    /**
     * @return the number of remaining cards in the deck
     */
    Integer getRemaining();

}
