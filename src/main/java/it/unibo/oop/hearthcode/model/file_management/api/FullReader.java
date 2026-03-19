package it.unibo.oop.hearthcode.model.file_management.api;

import java.util.List;
import java.util.Optional;

/**
 * Represents a generic reader that can read the entire content of a source.
 * 
 * @param <T> the type of the elements read from the source
 */
@FunctionalInterface
public interface FullReader<T> {

    /**
     * Reads all the lines from a source.
     * 
     * @return an Optional containing the lines read if they are present, empty otherwise
     */
    Optional<List<T>> readAll();

}
