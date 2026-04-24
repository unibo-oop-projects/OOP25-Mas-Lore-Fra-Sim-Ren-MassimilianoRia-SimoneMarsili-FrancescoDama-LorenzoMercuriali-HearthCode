package it.unibo.oop.hearthcode.view.api;

import java.util.List;

import javax.swing.JComponent;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * UI area representing a player and the related match information.
 */
public interface PlayerArea {

    /**
     * Returns the Swing component representing this player area.
     *
     * @return the root component of the player area
     */
    JComponent getComponent();

    /**
     * Returns the player identifier.
     *
     * @return the player identifier
     */
    PlayerId getPlayerId();

    /**
     * Returns the specific army card associated with the given identifier.
     *
     * @param cardId the identifier of the card to return
     * @return the matching army card component
     */
    CardComponent getArmyCard(CardId cardId);

    /**
     * Returns the cards currently present in the army.
     *
     * @return the list of army card components
     */
    List<CardComponent> getArmyCards();

    /**
     * Returns the cards currently present in the hand.
     *
     * @return the list of hand card components
     */
    List<CardComponent> getHandCards();

    /**
     * Returns the component containing the army area.
     *
     * @return the army area component
     */
    JComponent getArmyAreaComponent();

    /**
     * Removes a card from the army.
     * 
     * @param cardId the identifier of the card to remove
     */
    void removeArmyCard(CardId cardId);

    /**
     * Initializes the player's health values.
     *
     * @param health the maximum health value, also used as initial current health
     */
    void initHealth(int health);

    /**
     * Updates the current health shown for the player.
     *
     * @param currentHealth the new current health value
     */
    void setCurrentHealth(int currentHealth);

    /**
     * Updates the mana values shown for the player.
     *
     * @param newCurrentMana the current mana value
     * @param newMaxMana the maximum mana value
     */
    void setMana(int newCurrentMana, int newMaxMana);

    /**
     * Resets the card counters shown for the player.
     */
    void resetCardCounters();

    /**
     * Updates the card counters after a card has been drawn.
     */
    void registerCardDrawn();

    /**
     * Updates the card counters after a drawn card has been burned.
     */
    void registerCardBurned();

    /**
     * Updates the card counters after a card has been placed.
     */
    void registerCardPlaced();

    /**
     * Updates the card counters after a card has been destroyed.
     */
    void registerCardDestroyed();

    /**
     * Adds a card to the hand.
     *
     * @param card the card component to add
     */
    void addHandCard(CardComponent card);

    /**
     * Moves a card from the hand to the army.
     *
     * @param cardId the identifier of the card to place
     */
    void placeCard(CardId cardId);

}
