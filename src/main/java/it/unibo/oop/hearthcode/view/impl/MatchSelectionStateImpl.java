package it.unibo.oop.hearthcode.view.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.view.api.MatchSelectionState;

/**
 * Default implementation of {@link MatchSelectionState}.
 */
public final class MatchSelectionStateImpl implements MatchSelectionState {

    private CardId handCard;
    private CardId attacker;
    private CardId target;

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.handCard = null;
        this.attacker = null;
        this.target = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final CardId cardId) {
        return Objects.equals(this.handCard, cardId)
            || Objects.equals(this.attacker, cardId)
            || Objects.equals(this.target, cardId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public CardId getHandCard() {
        return this.handCard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearHandCard() {
        this.handCard = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleHandCard(final CardId cardId) {
        if (Objects.equals(this.handCard, cardId)) {
            this.handCard = null;
            return;
        }
        this.handCard = cardId;
        this.attacker = null;
        this.target = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardId getAttacker() {
        return this.attacker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAttacker() {
        this.attacker = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardId getTarget() {
        return this.target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearTarget() {
        this.target = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearCombatSelection() {
        this.attacker = null;
        this.target = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleAttacker(final CardId cardId) {
        if (Objects.equals(this.attacker, cardId)) {
            this.attacker = null;
            this.target = null;
            return;
        }
        this.handCard = null;
        this.attacker = cardId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleTarget(final CardId cardId) {
        if (Objects.equals(this.target, cardId)) {
            this.target = null;
            return;
        }
        this.target = cardId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
