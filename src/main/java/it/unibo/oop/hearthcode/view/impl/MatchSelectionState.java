package it.unibo.oop.hearthcode.view.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * Stores the cards currently selected in the match scene.
 */
final class MatchSelectionState {

    private CardId handCard;
    private CardId attacker;
    private CardId target;

    public void clear() {
        this.handCard = null;
        this.attacker = null;
        this.target = null;
    }

    public boolean contains(final CardId cardId) {
        return Objects.equals(this.handCard, cardId)
            || Objects.equals(this.attacker, cardId)
            || Objects.equals(this.target, cardId);
    }

    public void remove(final CardId cardId) {
        if (Objects.equals(this.handCard, cardId)) {
            this.handCard = null;
        }
        if (Objects.equals(this.attacker, cardId)) {
            this.attacker = null;
        }
        if (Objects.equals(this.target, cardId)) {
            this.target = null;
        }
    }

    public CardId getHandCard() {
        return this.handCard;
    }

    public void clearHandCard() {
        this.handCard = null;
    }

    public void toggleHandCard(final CardId cardId) {
        if (Objects.equals(this.handCard, cardId)) {
            this.handCard = null;
            return;
        }
        this.handCard = cardId;
        this.attacker = null;
        this.target = null;
    }

    public CardId getAttacker() {
        return this.attacker;
    }

    public void clearAttacker() {
        this.attacker = null;
    }

    public CardId getTarget() {
        return this.target;
    }

    public void clearTarget() {
        this.target = null;
    }

    public void clearCombatSelection() {
        this.attacker = null;
        this.target = null;
    }

    public void toggleAttacker(final CardId cardId) {
        if (Objects.equals(this.attacker, cardId)) {
            this.attacker = null;
            this.target = null;
            return;
        }
        this.handCard = null;
        this.attacker = cardId;
    }

    public void toggleTarget(final CardId cardId) {
        if (Objects.equals(this.target, cardId)) {
            this.target = null;
            return;
        }
        this.target = cardId;
    }

    public List<CardId> toSelectedCards() {
        if (this.handCard != null) {
            return List.of(this.handCard);
        }
        if (this.attacker == null) {
            return List.of();
        }
        if (this.target == null) {
            return List.of(this.attacker);
        }
        return List.of(this.attacker, this.target);
    }

}
