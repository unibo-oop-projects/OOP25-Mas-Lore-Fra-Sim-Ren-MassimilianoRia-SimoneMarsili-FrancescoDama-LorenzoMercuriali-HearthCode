package it.unibo.oop.hearthcode.model.boardgame.api;

import java.util.Map;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * An interface for {@link ObservableGame} observers.
 */
public interface GameObserver {

    /**
     * Notifies that the match started.
     * 
     * @param startingPlayer the player whose turn starts the match
     * @param playersInitialState a map matching players with their initial state
     */
    void onGameStarted(PlayerId startingPlayer, Map<PlayerId, PlayerInitialState> playersInitialState);

    /**
     * Notifies that a turn is ended and a new one starts.
     * 
     * @param nextPlayer the player whose turn has started
     */
    void onTurnSwitch(PlayerId nextPlayer);

    /**
     * Notifies that a creature was drawn.
     * 
     * @param playerId the player who drawn the creature
     * @param drawnCard the drawn card
     * @param def the {@link CreatureDefinition} of the drawn creature
     */
    void onCreatureDrawn(PlayerId playerId, CardId drawnCard, CreatureDefinition def);

    /**
     * Notifies that a card was placed in the board.
     * 
     * @param playerId the player who placed the card
     * @param placedCard the placed card
     */
    void onCardPlaced(PlayerId playerId, CardId placedCard);

    /**
     * Notifies that the health of a card has changed.
     * 
     * @param playerId the player owner of the card
     * @param cardId card whose health has changed
     * @param newHealth the new health of the card
     */
    void onCardHealthChanged(PlayerId playerId, CardId cardId, int newHealth);

    /**
     * Notifies that a card was destroyed.
     * 
     * @param playerId the player owner of the card
     * @param cardId the destroyed card
     */
    void onCardDestroyed(PlayerId playerId, CardId cardId);

    /**
     * Notifies that the health of a player has changed.
     * 
     * @param playerId the player whose health has changed
     * @param newHealth the new health of the player
     */
    void onPlayerHealthChanged(PlayerId playerId, int newHealth);

    /**
     * Notifies that the Mana of a player has changed.
     * 
     * @param playerId the player whose Mana has changed
     * @param actualMana the new actual mana of the player
     * @param manaLimit the mana limit of the player
     */
    void onManaChanged(PlayerId playerId, int actualMana, int manaLimit);

    /**
     * Notifies that a Card is exhausted and cannot be used.
     * 
     * @param playerId the player owner of the exhausted card
     * @param exhaustedCard the exhausted card
     */
    void onCardExhausted(PlayerId playerId, CardId exhaustedCard);

    /**
     * Notifies that the card was burned during the drawing action.
     * 
     * @param playerId the player to be notified
     */
    void onCardBurned(PlayerId playerId);

}
