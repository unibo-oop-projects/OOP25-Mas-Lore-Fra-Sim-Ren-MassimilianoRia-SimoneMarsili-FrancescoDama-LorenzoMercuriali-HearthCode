package it.unibo.oop.heartcode.model.deck.api;

/**
 * It modelizes an interface for parsing a string into a T type object.
 * 
 * @param <T> the type of parsed string
 */
@FunctionalInterface
public interface Parser<T> {
    /**
     * Parses a single string into the corresponding object.
     * 
     * @param line the line to be parsed
     * @return the parsed object
     */
    T parseLine(String line);
}
