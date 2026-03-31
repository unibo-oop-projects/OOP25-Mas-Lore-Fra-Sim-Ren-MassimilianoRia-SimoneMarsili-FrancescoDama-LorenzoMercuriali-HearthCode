package it.unibo.oop.hearthcode.view.api;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.view.impl.MatchCardZone;

/**
 * Tracks the match metadata associated with a rendered card.
 */
public interface MatchCardSlot {

    /**
     * Returns the rendered card component.
     *
     * @return the tracked card component
     */
    CardComponent getCard();

    /**
     * Returns the owner of the tracked card.
     *
     * @return the owning player identifier
     */
    PlayerId getOwner();

    /**
     * Returns the mana cost of the tracked card.
     *
     * @return the mana cost
     */
    int getManaCost();

    /**
     * Returns the current zone of the tracked card.
     *
     * @return the current card zone
     */
    MatchCardZone getZone();

    /**
     * Moves the tracked card to the army zone.
     */
    void moveToArmy();

    /**
     * Marks the tracked card as ready for a new turn.
     */
    void wakeUp();

    /**
     * Marks the tracked card as exhausted after an action.
     */
    void exhaust();

    /**
     * Returns whether the tracked card cannot currently be interacted with.
     *
     * @return {@code true} when the card is not interactable
     */
    boolean isDormantForInteraction();

    /**
     * Returns whether the tracked card should be shown as resting.
     *
     * @return {@code true} when the card should appear resting
     */
    boolean isDormantForVisuals();

}
