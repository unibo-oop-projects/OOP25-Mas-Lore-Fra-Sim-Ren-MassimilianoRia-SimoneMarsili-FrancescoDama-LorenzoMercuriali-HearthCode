package it.unibo.oop.hearthcode.model.creature.impl;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardType;
import it.unibo.oop.hearthcode.model.creature.api.Creature;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;

/**
 * A simple {@link CreatureImpl} factory.
 */
public final class CreatureImplFactory {

    private final IdGenerator generator;

    /**
     * Constructs a new CreatureImplFactory.
     * 
     * @param generator the {@link IdGenerator} used to initialize creatures
     */
    public CreatureImplFactory(final IdGenerator generator) {
        this.generator = generator;
    }

    /**
     * Creates a new {@link CreatureImpl} from its {@link CreatureDefinition}.
     * 
     * @param definition the definition of the creature to create
     * @return the created creature
     */
    public Creature createFromDefinition(final CreatureDefinition definition) {
        return new CreatureImpl(
            definition,
            new CardId(CardType.CREATURE, this.generator.nextId())
        );
    }
}
