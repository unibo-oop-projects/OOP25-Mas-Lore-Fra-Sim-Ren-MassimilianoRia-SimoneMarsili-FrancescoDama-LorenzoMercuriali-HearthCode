package it.unibo.oop.hearthcode.model.boardgame.api;

import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * It represents the main board of the match.
 */
public interface BoardGame extends ObservableGame {

    /**
     * @param playerId the identifier of the player
     * @return the actual health of the player
     */
    int getPlayerHealth(PlayerId playerId);

    /**
     * @param playerId the identifier of the player
     * @return the actual mana amount of the player
     */
    int getPlayerActualMana(PlayerId playerId);

    /**
     * @param playerId the identifier of the player
     * @return a copy of the cards held in the hand of the player
     */
    List<CardState> getHandCardsCopies(PlayerId playerId);

    /**
     * @param playerId the identifier of the player
     * @return a copy of the cards held in the army of the player
     */
    List<CardState> getArmyCardsCopies(PlayerId playerId);

    /**
     * It allows to start the game.
     */
    void startGame();

    /**
     * @return the winner of the game if present, empty otherwise
     */
    Optional<PlayerId> getWinner();

    /**
     * Starts combat between the specified cards.
     * 
     * @param attackingIdCard the id of the attacking card
     * @param defendingIdCard the id of the defending card
     */
    void attackCard(CardId attackingIdCard, CardId defendingIdCard);

    /**
     * Starts combat between the specified card and the enemy hero.
     * 
     * @param attackingIdCard the attacking card
     */
    void attackHero(CardId attackingIdCard);

    /**
     * Places the card selected by the player in their army.
     * 
     * @param selectedIdCard the id of the card to be placed
     */
    void place(CardId selectedIdCard);

    /**
     * Performs the actions required to switch turns.
     */
    void switchTurn();

}
