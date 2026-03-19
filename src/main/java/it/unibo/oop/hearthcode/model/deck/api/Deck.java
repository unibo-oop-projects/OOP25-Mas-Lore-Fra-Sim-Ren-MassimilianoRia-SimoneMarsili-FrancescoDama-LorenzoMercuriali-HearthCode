package it.unibo.oop.hearthcode.model.deck.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.Card;

/**
 * Represents the set of cards that can be drawn by a player.
 */
public interface Deck {

    /**
     * Represents the act of drawing a card from the deck.
     * 
     * @return the drawn card
     */
    Optional<Card> draw();

    /**
     * @return the number of remaining cards in the deck
     */
    Integer getRemaining();

}
