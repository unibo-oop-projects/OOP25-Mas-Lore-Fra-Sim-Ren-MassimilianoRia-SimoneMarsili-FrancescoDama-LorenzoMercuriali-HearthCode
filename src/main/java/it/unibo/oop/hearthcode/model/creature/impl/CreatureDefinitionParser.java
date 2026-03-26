package it.unibo.oop.hearthcode.model.creature.impl;

import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.creature.api.Parser;

/**
 * A {@link Parser} implementation for {@link CreatureDefinition}.
 */
public class CreatureDefinitionParser implements Parser<CreatureDefinition> {

    private static final String SPLIT_CHAR = ",";
    private static final Integer N_FIELDS = 4;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreatureDefinition parseLine(final String line) {
        if (line == null) {
            throw new IllegalArgumentException("The given line was null.");
        }
        final String[] components = line.split(SPLIT_CHAR);
        if (components.length != N_FIELDS) {
            throw new IllegalArgumentException("Couldn't extract the correct number of fields.");
        }
        return new CreatureDefinition(
            components[0],
            parseStringToInt(components[1]),
            parseStringToInt(components[2]),
            parseStringToInt(components[3])
        );
    }

    private int parseStringToInt(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("Couldn't parse to int the string " + s, e);
        }
    }
}
