package it.unibo.oop.hearthcode.model.database.impl;

import it.unibo.oop.hearthcode.model.creature.impl.CreatureDefinitionParser;
import it.unibo.oop.hearthcode.model.file_management.impl.TextFileFullReader;

/**
 * A simple Factory for {@link CreatureDatabase}.
 */
public final class CreatureDatabaseFactory {

    private CreatureDatabaseFactory() { }

    /**
     * Static method for creating an entire database from a file.
     * 
     * @param fileName the name of the file containing the entities of this database
     * @return the new Database created
     */
    public static CreatureDatabase createFromFile(final String fileName) {
        final var stream = ClassLoader.getSystemResourceAsStream(fileName);
        if (stream == null) {
            throw new IllegalArgumentException("The resource " + fileName + " couldn't be found.");
        }
        final var allLines = new TextFileFullReader(stream).readAll();
        if (allLines.isEmpty()) {
            throw new IllegalArgumentException("The resource has no content: can't create empty database.");
        }
        final var parser = new CreatureDefinitionParser();
        return new CreatureDatabase(allLines.get().stream()
            .map(parser::parseLine)
            .toList()
        );
    }

}
