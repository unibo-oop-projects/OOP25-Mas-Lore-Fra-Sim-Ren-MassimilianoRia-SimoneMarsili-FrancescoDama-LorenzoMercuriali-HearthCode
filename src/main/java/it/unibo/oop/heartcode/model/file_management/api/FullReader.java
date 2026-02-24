package it.unibo.oop.heartcode.model.file_management.api;

import java.util.List;
import java.util.Optional;

/**
 * It modelizes a generic reader that can read the entire content of a specific source.
 * 
 * @param <T> the type of the elements read frome the source
 */
@FunctionalInterface
public interface FullReader<T> {

    /**
     * Read all the lines of a specific source.
     * 
     * @return an Optional containing the lines read if they are present, empty otherwise
     */
    Optional<List<T>> readAll();

}
