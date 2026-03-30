package it.unibo.oop.hearthcode.view.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.oop.hearthcode.model.creature.api.CardId;

final class MatchSelectionState {

    private CardId handCard;
    private CardId attacker;
    private CardId target;

    void clear() {
        this.handCard = null;
        this.attacker = null;
        this.target = null;
    }

    boolean contains(final CardId cardId) {
        return Objects.equals(this.handCard, cardId)
            || Objects.equals(this.attacker, cardId)
            || Objects.equals(this.target, cardId);
    }

    void remove(final CardId cardId) {
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

    CardId handCard() {
        return this.handCard;
    }

    void clearHandCard() {
        this.handCard = null;
    }

    void toggleHandCard(final CardId cardId) {
        if (Objects.equals(this.handCard, cardId)) {
            this.handCard = null;
            return;
        }
        this.handCard = cardId;
        this.attacker = null;
        this.target = null;
    }

    CardId attacker() {
        return this.attacker;
    }

    void clearAttacker() {
        this.attacker = null;
    }

    CardId target() {
        return this.target;
    }

    void clearTarget() {
        this.target = null;
    }

    void clearCombatSelection() {
        this.attacker = null;
        this.target = null;
    }

    void toggleAttacker(final CardId cardId) {
        if (Objects.equals(this.attacker, cardId)) {
            this.attacker = null;
            this.target = null;
            return;
        }
        this.handCard = null;
        this.attacker = cardId;
    }

    void toggleTarget(final CardId cardId) {
        if (Objects.equals(this.target, cardId)) {
            this.target = null;
            return;
        }
        this.target = cardId;
    }

    List<CardId> toSelectedCards() {
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
