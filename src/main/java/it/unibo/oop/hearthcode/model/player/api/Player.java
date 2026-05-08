package it.unibo.oop.hearthcode.model.player.api;

import java.util.List;

import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * It represents a player in the game.
 */
public interface Player {
    /**
     * @return the player id
     */

    PlayerId getId();

    /**
     * @return the actual amount of player's Mana
     */
    int getActualMana();

    /**
     * @return the amount of maximum Mana available for the turn
     */
    int getTurnManaLimit();

    /**
     * @return the amount of player's health
     */
    int getHealth();

    /**
     * @return a copy of the cards held in hand
     */
    List<CardState> getHandCardsCopies();

    /**
     * @return the maximum number of cards the player can hold in hand
     */
    int getHandCardsLimit();

    /**
     * @return the number of cards currently available in the deck
     */
    int getDeckCardsCount();

    /**
     * Decreases the player's current health.
     * 
     * @param amount the amount of health to be subtracted
     */
    void decreaseHealth(int amount);

    /**
     * It increments the Mana limit of the player by one and refreshes the actual one.
     */
    void incrementMana();

    /**
     * The action of playing a card taken from the player's hand. 
     * 
     * @param cardId the id of the card to be played
     * @return the requested card
     */
    Card playCard(CardId cardId);

    /**
     * The action of drawing a card by the player.
     * 
     * @return the outcome of the draw
     */
    DrawCardResult drawCard();

}
