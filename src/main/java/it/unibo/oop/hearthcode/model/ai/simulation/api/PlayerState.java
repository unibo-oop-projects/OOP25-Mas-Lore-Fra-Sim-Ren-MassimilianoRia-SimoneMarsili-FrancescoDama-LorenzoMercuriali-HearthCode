package it.unibo.oop.hearthcode.model.ai.simulation.api;

import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Representation of a player state used by the AI.
 */
public interface PlayerState {

    /**
     * @return the player identifier
     */
    PlayerId getPlayerId();

    /**
     * @return the player health
     */
    int getPlayerHealth();

    /**
     * @return the current mana value
     */
    int getPlayerActualMana();

    /**
     * @return an optional containing the player's hand when available
     */
    Optional<List<CardState>> getPlayerHand();

    /**
     * @return the list of cards currently deployed in the army
     */
    List<CardState> getPlayerArmy();

    /**
     * @param cardId the identifier of the card
     * @return the state of the card requested
     */
    Optional<CardState> getHandCard(CardId cardId);

    /**
     * @param cardId the identifier of the card
     * @return the state of the card requested
     */
    Optional<CardState> getArmyCard(CardId cardId);

    /**
     * Simulates a reduction of the player's health.
     * 
     * @param damage the amount of health to decrease
     */
    void damagePlayer(int damage);

    /**
     * Simulates a reduction of the player's available mana.
     * 
     * @param mana the amount of mana to decrease
     */
    void consumeMana(int mana);

    /**
     * Simulates a card placing.
     * 
     * @param cardId the identifier of the card
     */
    void placeCard(CardId cardId);

    /**
     * Simulates a card destroying.
     * 
     * @param cardId the identifier of the card
     */
    void destroyArmyCard(CardId cardId);

}
