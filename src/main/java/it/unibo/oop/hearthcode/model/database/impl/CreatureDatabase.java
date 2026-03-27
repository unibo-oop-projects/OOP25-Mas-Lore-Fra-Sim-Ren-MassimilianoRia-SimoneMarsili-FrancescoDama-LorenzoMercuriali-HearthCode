package it.unibo.oop.hearthcode.model.database.impl;

import java.util.List;

import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.database.api.Database;

/**
 * A specific implementation of {@link Database} that contains {@link CreatureDefinition}.
 */
public final class CreatureDatabase implements Database<CreatureDefinition> {

    private final List<CreatureDefinition> definitions;

    /**
     * Constructs a new creature database from a collection.
     * 
     * @param definitions the list of {@link CreatureDefinition} used to create the database
     */
    CreatureDatabase(final List<CreatureDefinition> definitions) {
        this.definitions = definitions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CreatureDefinition> getAll() {
        return List.copyOf(this.definitions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return this.definitions.size();
    }

}
