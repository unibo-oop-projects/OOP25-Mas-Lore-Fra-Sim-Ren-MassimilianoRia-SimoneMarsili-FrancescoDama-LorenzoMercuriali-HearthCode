package it.unibo.oop.hearthcode.view.api;

import java.util.List;

import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * Stores the cards currently selected in the match scene.
 */
public interface MatchSelectionState {

    /**
     * Clears every stored selection.
     */
    void clear();

    /**
     * Returns whether the given card is part of the current selection.
     *
     * @param cardId the card identifier to check
     * @return {@code true} when the card is selected
     */
    boolean contains(CardId cardId);

    /**
     * Removes the given card from the current selection when present.
     *
     * @param cardId the card identifier to remove
     */
    void remove(CardId cardId);

    /**
     * Returns the selected hand card.
     *
     * @return the selected hand card, or {@code null} when absent
     */
    CardId getHandCard();

    /**
     * Clears the selected hand card.
     */
    void clearHandCard();

    /**
     * Toggles the selected hand card.
     *
     * @param cardId the card identifier to toggle
     */
    void toggleHandCard(CardId cardId);

    /**
     * Returns the selected attacker.
     *
     * @return the attacker card identifier, or {@code null} when absent
     */
    CardId getAttacker();

    /**
     * Clears the selected attacker.
     */
    void clearAttacker();

    /**
     * Returns the selected target.
     *
     * @return the target card identifier, or {@code null} when absent
     */
    CardId getTarget();

    /**
     * Clears the selected target.
     */
    void clearTarget();

    /**
     * Clears the current combat selection.
     */
    void clearCombatSelection();

    /**
     * Toggles the selected attacker.
     *
     * @param cardId the attacker identifier to toggle
     */
    void toggleAttacker(CardId cardId);

    /**
     * Toggles the selected target.
     *
     * @param cardId the target identifier to toggle
     */
    void toggleTarget(CardId cardId);

    /**
     * Returns the selected cards in the order expected by the controller.
     *
     * @return the ordered list of selected cards
     */
    List<CardId> toSelectedCards();

}
