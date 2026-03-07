package it.unibo.oop.hearthcode.model.boardgame.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * It represents the main board of the match.
 */
public interface BoardGame {

    /**
     * It allows to start the fight between the specified cards.
     * 
     * @param attackingIdCard the id of the attacking card
     * @param defendingIdCard the id of the defending card
     */
    void fight(Optional<CardId> attackingIdCard, Optional<CardId> defendingIdCard);

    /**
     * It allows to place the card selected by the player in his own army.
     * 
     * @param selectedIdCard the id of the card to be placed.
     */
    void place(Optional<CardId> selectedIdCard);

}
