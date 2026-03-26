package it.unibo.oop.hearthcode.model.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;

/**
 * A simple test for {@link CreatureDatabase}.
 */
final class TestCreatureDatabase {

    private static final String TEST_FILE = "creatures.txt";
    private static final String TEST_NAME = "reckless_rocketeer";
    private static final int TEST_HEALTH = 2;
    private static final int TEST_ATTACK = 5;
    private static final int TEST_MANA = 6;
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
        assertEquals(new CreatureDefinition("murloc", 1, 2, 1),
            this.db.getAll().get(0)
        );
        assertEquals(new CreatureDefinition(TEST_NAME, TEST_HEALTH, TEST_ATTACK, TEST_MANA),
            this.db.getAll().get(this.db.getAll().size() - 1)
        );
        assertThrows(UnsupportedOperationException.class,
            () -> this.db.getAll().add(new CreatureDefinition("Name", 1, 1, 1))
        );
    }
}
