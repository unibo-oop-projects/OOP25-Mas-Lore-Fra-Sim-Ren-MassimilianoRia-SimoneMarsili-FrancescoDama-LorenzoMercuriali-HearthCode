package it.unibo.oop.hearthcode.model.creature.impl;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardState;

/**
 * Implementation of {@link CardState}.
 */
public class CardStateImpl implements CardState {

    private final CardId cardId;
    private final int manaCost;
    private final int attackValue;
    private int health;
    private boolean usable;

    /**
     * Creates a card state.
     *
     * @param cardId the card identifier
     * @param manaCost the mana cost of the card
     * @param attackValue the attack value of the card
     * @param health the current health of the card
     * @param usable whether the card can currently be used
     */
    public CardStateImpl(
        final CardId cardId,
        final int manaCost,
        final int attackValue,
        final int health,
        final boolean usable
    ) {
        this.cardId = cardId;
        this.manaCost = manaCost;
        this.attackValue = attackValue;
        this.health = health;
        this.usable = usable;
    }

    /**
     * Copy constructor.
     *
     * @param other the source card state
     */
    public CardStateImpl(final CardState other) {
        this(
            other.getCardId(),
            other.getManaCost(),
            other.getAttackValue(),
            other.getHealth(),
            other.isUsable()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardId getCardId() {
        return this.cardId;
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
    public int getHealth() {
        return this.health;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttackValue() {
        return this.attackValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUsable() {
        return this.usable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void damage(final int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative.");
        }
        this.health = Math.max(0, this.health - damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exhaust() {
        this.usable = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void awaken() {
        this.usable = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDead() {
        return this.health <= 0;
    }

}
