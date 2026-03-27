package it.unibo.oop.hearthcode.model.ai.impl;

import it.unibo.oop.hearthcode.model.ai.api.CardState;
import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * Implementation of {@link CardState}.
 */
public class CardStateImpl implements CardState {

    private final CardId cardId;
    private final int manaCost;
    private final int health;
    private final int attackValue;
    private final boolean usable;

    /**
     * Creates a card state.
     *
     * @param cardId the card identifier
     * @param manaCost the mana cost of the card
     * @param health the current health of the card
     * @param attackValue the attack value of the card
     * @param usable whether the card can currently be used
     */
    public CardStateImpl(
        final CardId cardId,
        final int manaCost,
        final int health,
        final int attackValue,
        final boolean usable
    ) {
        this.cardId = cardId;
        this.manaCost = manaCost;
        this.health = health;
        this.attackValue = attackValue;
        this.usable = usable;
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

}
