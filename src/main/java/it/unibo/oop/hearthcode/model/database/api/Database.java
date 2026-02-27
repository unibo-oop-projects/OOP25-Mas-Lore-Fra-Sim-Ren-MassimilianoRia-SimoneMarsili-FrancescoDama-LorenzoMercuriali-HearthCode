package it.unibo.oop.hearthcode.model.database.api;

import java.util.List;

/**
 * Modelizes a generic database.
 * 
 * @param <T> the type of the entities of this database
 */
public interface Database<T> {

    /**
     * @return all the elements of this database
     */
    List<T> getAll();

    /**
     * @return the size of this database
     */
    int size();
}
