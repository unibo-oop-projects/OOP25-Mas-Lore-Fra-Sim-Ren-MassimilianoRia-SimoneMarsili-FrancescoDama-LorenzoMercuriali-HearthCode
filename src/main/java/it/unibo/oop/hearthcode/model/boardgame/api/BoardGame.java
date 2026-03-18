package it.unibo.oop.hearthcode.model.boardgame.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * It represents the main board of the match.
 */
public interface BoardGame {

    /**
     * @return whether the game is over
     */
    boolean isOver();

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
    void attackCard(Optional<CardId> attackingIdCard, Optional<CardId> defendingIdCard);

    /**
     * Starts combat between the specified card and the enemy hero.
     * 
     * @param attackingIdCard the attacking card
     * @param defendingPlayer the defending player
     */
    void attackHero(Optional<CardId> attackingIdCard, Optional<PlayerId> defendingPlayer);

    /**
     * Places the card selected by the player in their army.
     * 
     * @param selectedIdCard the id of the card to be placed
     * @param placingPlayer the player who placed the card
     */
    void place(Optional<CardId> selectedIdCard, Optional<PlayerId> placingPlayer);

    /**
     * Performs the actions required to switch turns.
     */
    void switchTurn();

}
