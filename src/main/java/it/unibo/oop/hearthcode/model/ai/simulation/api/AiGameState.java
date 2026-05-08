package it.unibo.oop.hearthcode.model.ai.simulation.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * A mutable AI game state used to simulate actions during AI planning.
 */
public interface AiGameState {

    /**
     * @return a deep copy of this game state
     */
    AiGameState copy();

    /**
     * @param playerId the identifier of the player
     * @return the player state associated with the given identifier
     */
    PlayerState getPlayerState(PlayerId playerId);

    /**
     * Decreases the health of the specified player.
     *
     * @param playerId the player identifier
     * @param damage the amount of damage
     */
    void damagePlayer(PlayerId playerId, int damage);

    /**
     * Decreases the current mana of the specified player.
     *
     * @param playerId the player identifier
     * @param mana the mana amount to consume
     */
    void consumeMana(PlayerId playerId, int mana);

    /**
     * Moves a card from hand to army for the specified player.
     * The card becomes not usable when placed.
     *
     * @param playerId the player identifier
     * @param cardId the card to place
     */
    void placeCard(PlayerId playerId, CardId cardId);

    /**
     * Decreases the health of the specified card.
     *
     * @param playerId the owner of the card
     * @param cardId the card identifier
     * @param damage the amount of damage
     */
    void damageCard(PlayerId playerId, CardId cardId, int damage);

    /**
     * Marks the specified card as exhausted.
     *
     * @param playerId the owner of the card
     * @param cardId the card identifier
     */
    void exhaustCard(PlayerId playerId, CardId cardId);

    /**
     * Removes the specified card from the player's army.
     *
     * @param playerId the owner of the card
     * @param cardId the card identifier
     */
    void destroyCard(PlayerId playerId, CardId cardId);

    /**
     * @param playerId the owner of the army
     * @param cardId the card identifier
     * @return the card state if present
     */
    Optional<CardState> getArmyCard(PlayerId playerId, CardId cardId);

    /**
     * @param playerId the owner of the hand
     * @param cardId the card identifier
     * @return the card state if present
     */
    Optional<CardState> getHandCard(PlayerId playerId, CardId cardId);

}
