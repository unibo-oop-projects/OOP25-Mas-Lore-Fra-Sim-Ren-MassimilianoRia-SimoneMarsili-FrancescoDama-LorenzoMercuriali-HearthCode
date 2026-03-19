package it.unibo.oop.hearthcode.model.creature.impl;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Creature;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;

/**
 * An implementation of {@link Creature}.
 */
public class CreatureImpl implements Creature {

    private final CardId id;
    private final CreatureDefinition definition;
    private int currentHealth;

    CreatureImpl(final CreatureDefinition definition, final CardId id) {
        this.definition = definition;
        this.id = id;
        this.currentHealth = this.definition.health();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardId getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.definition.name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getManaCost() {
        return this.definition.manaCost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getHealth() {
        return this.currentHealth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getAttackValue() {
        return this.definition.attackValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer decreaseHealth(final Integer damage) {
        this.currentHealth = Math.max(0, this.currentHealth - damage);
        return this.currentHealth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final CreatureImpl other = (CreatureImpl) obj;
        return this.id.equals(other.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
