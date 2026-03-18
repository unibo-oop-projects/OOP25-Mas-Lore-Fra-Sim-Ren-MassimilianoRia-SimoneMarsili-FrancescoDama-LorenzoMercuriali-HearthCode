package it.unibo.oop.hearthcode.model.creature.api;

/**
 * An interface for parsing a string into an object of type {@code T}.
 * 
 * @param <T> the type produced by the parser
 */
@FunctionalInterface
public interface Parser<T> {
    /**
     * Parses a single string into the corresponding object.
     * 
     * @param line the line to parse
     * @return the parsed object
     */
    T parseLine(String line);
}
