package it.unibo.oop.hearthcode.model.match.impl;

/**
 * A simple Integer ID generator.
 * It basicly acts as a counter.
 */
public final class IdGenerator {

    private int nextId;

    /**
     * It constructs a new IdGenerator that starts counting from 1.
     */
    public IdGenerator() {
        this.nextId = 1;
    }

    /**
     * @return the next id
     */
    public int nextId() {
        final var id = this.nextId;
        this.nextId++;
        return id;
    }
}
