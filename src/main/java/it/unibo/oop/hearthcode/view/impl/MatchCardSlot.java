package it.unibo.oop.hearthcode.view.impl;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;

/**
 * Tracks the match metadata associated with a rendered card.
 */
final class MatchCardSlot {

    private final PlayerId owner;
    private final int manaCost;
    private MatchCardZone zone;
    private boolean sleeping;
    private boolean exhausted;

    /**
     * Builds the tracked state for a card shown in the match scene.
     *
     * @param owner the owner of the card
     * @param manaCost the mana cost of the card
     * @param zone the initial zone of the card
     */
    MatchCardSlot(
        final PlayerId owner,
        final int manaCost,
        final MatchCardZone zone
    ) {
        this.owner = owner;
        this.manaCost = manaCost;
        this.zone = zone;
    }

    PlayerId getOwner() {
        return this.owner;
    }

    int getManaCost() {
        return this.manaCost;
    }

    MatchCardZone getZone() {
        return this.zone;
    }

    void moveToArmy() {
        this.zone = MatchCardZone.ARMY;
        this.sleeping = true;
        this.exhausted = false;
    }

    void wakeUp() {
        this.sleeping = false;
        this.exhausted = false;
    }

    void exhaust() {
        this.exhausted = true;
    }

    boolean isDormantForInteraction() {
        return this.sleeping || this.exhausted;
    }

    boolean isDormantForVisuals() {
        if (this.owner.type() == PlayerType.HUMAN_PLAYER) {
            return this.isDormantForInteraction();
        }
        return this.sleeping;
    }

}
