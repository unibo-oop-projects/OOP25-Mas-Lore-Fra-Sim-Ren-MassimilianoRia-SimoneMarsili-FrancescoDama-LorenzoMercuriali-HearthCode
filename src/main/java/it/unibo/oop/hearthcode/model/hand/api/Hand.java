package it.unibo.oop.hearthcode.model.hand.api;

import java.util.List;

import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * It represents the hand of the player.
 */
public interface Hand {

    /**
     * @return the maximum number of cards in the player's hand
     */
    int getMaximumSize();

    /**
     * @return the number of cards in the player's hand
     */
    int getActualSize();

    /**
     * It returns the card with the specified id.
     * 
     * @param cardId the id of the card
     * @return the card
     */
    Card getCard(CardId cardId);

    /**
     * @return a copy of the cards held
     */
    List<CardState> getCardsCopies();

    /**
     * It adds the card in the hand.
     * 
     * @param card the card to be added
     */
    void addCard(Card card);

    /**
     * It removes from the hand the card specified by its own id.
     * 
     * @param cardId the id of the card
     * @return the card removed
     */
    Card removeCard(CardId cardId);
}
