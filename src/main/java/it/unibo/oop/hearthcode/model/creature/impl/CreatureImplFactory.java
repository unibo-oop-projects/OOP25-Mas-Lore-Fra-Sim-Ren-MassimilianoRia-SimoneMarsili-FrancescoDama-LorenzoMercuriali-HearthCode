package it.unibo.oop.hearthcode.model.creature.impl;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Creature;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.game.impl.IdGenerator;

/**
 * A simple {@link CreatureImpl} factory.
 */
public final class CreatureImplFactory {

    private final IdGenerator generator;

    /**
     * It constructs a new CreatureImplFactory.
     * 
     * @param generator the {@link IdGenerator} used to initialize creatures
     */
    public CreatureImplFactory(final IdGenerator generator) {
        this.generator = generator;
    }

    /**
     * It creates a new {@link CreatureImpl} starting from its {@link CreatureDefinition}.
     * 
     * @param definition the CreatureDefinition of the creature to create
     * @return the creature created
     */
    public Creature createFromDefinition(final CreatureDefinition definition) {
        return new CreatureImpl(
            definition,
            new CardId(this.generator.nextId())
        );
    }
}
