package it.unibo.oop.hearthcode.view.impl;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.MatchCardSlot;

/**
 * Default implementation of {@link MatchCardSlot}.
 */
public final class MatchCardSlotImpl implements MatchCardSlot {

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
    public MatchCardSlotImpl(
        final PlayerId owner,
        final int manaCost,
        final MatchCardZone zone
    ) {
        this.owner = owner;
        this.manaCost = manaCost;
        this.zone = zone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerId getOwner() {
        return this.owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getManaCost() {
        return this.manaCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchCardZone getZone() {
        return this.zone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveToArmy() {
        this.zone = MatchCardZone.ARMY;
        this.sleeping = true;
        this.exhausted = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void wakeUp() {
        this.sleeping = false;
        this.exhausted = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exhaust() {
        this.exhausted = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDormantForInteraction() {
        return this.sleeping || this.exhausted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDormantForVisuals() {
        if (this.owner.type() == PlayerType.HUMAN_PLAYER) {
            return this.isDormantForInteraction();
        }
        return this.sleeping;
    }

}
