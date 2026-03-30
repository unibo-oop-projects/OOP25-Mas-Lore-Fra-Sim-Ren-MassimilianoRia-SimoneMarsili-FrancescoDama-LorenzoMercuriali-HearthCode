package it.unibo.oop.hearthcode.view.impl;

import java.io.Serializable;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.CardComponent;

final class MatchCardSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    private final CardComponent card;
    private final PlayerId owner;
    private final int manaCost;
    private MatchCardZone zone;
    private boolean sleeping;
    private boolean exhausted;

    MatchCardSlot(
        final CardComponent card,
        final PlayerId owner,
        final int manaCost,
        final MatchCardZone zone
    ) {
        this.card = card;
        this.owner = owner;
        this.manaCost = manaCost;
        this.zone = zone;
    }

    CardComponent card() {
        return this.card;
    }

    PlayerId owner() {
        return this.owner;
    }

    int manaCost() {
        return this.manaCost;
    }

    MatchCardZone zone() {
        return this.zone;
    }

    void moveToArmy() {
        this.zone = MatchCardZone.ARMY;
        this.sleeping = true;
        this.exhausted = false;
        this.card.setFaceUp(true);
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
