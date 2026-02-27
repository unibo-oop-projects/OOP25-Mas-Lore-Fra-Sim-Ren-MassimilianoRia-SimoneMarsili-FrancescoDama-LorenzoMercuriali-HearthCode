package it.unibo.oop.hearthcode.model.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.deck.impl.CreatureDefinition;

/**
 * A simple test for {@link CreatureDatabase}.
 */
final class TestCreatureDatabase {

    private static final String TEST_FILE = "creatures.txt";
    private static final int N_CREATURES = 10;
    private CreatureDatabase db;

    @BeforeEach
    void initTest() {
        try {
            this.db = CreatureDatabaseFactory.createFromFile(TEST_FILE);
        } catch (final IllegalArgumentException e) {
            fail(e);
        }
    }

    @Test
    void testDb() {
        assertEquals(N_CREATURES, this.db.size());
        assertEquals(new CreatureDefinition("Murloc", 1, 2, 1), this.db.getAll().get(0));
        assertThrows(UnsupportedOperationException.class,
            () -> this.db.getAll().add(new CreatureDefinition("Name", 1, 1, 1))
        );
    }
}
