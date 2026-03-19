package it.unibo.oop.hearthcode.model.boardgame.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * It represents the main board of the match.
 */
public interface BoardGame {

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
