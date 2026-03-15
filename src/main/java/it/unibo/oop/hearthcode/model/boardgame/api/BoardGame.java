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
     * @return return whether the game is over or not
     */
    boolean isOver();

    /**
     * @return return the winner of the game if present, empty otherwise
     */
    Optional<PlayerId> getWinner();

    /**
     * It allows to start the fight between the specified cards.
     * 
     * @param attackingIdCard the id of the attacking card
     * @param defendingIdCard the id of the defending card
     */
    void attackCard(Optional<CardId> attackingIdCard, Optional<CardId> defendingIdCard);

    /**
     * It allows to start the fight between the specified card and the enemy hero.
     * 
     * @param attackingIdCard the attacking card
     * @param defendingPlayer the defending player
     */
    void attackHero(Optional<CardId> attackingIdCard, Optional<PlayerId> defendingPlayer);

    /**
     * It allows to place the card selected by the player in his own army.
     * 
     * @param selectedIdCard the id of the card to be placed
     * @param placingPlayer the player who placed the card
     */
    void place(CardId selectedIdCard, PlayerId placingPlayer);

    /**
     * Makes actions to switch the match turn.
     */
    void switchTurn();

}
