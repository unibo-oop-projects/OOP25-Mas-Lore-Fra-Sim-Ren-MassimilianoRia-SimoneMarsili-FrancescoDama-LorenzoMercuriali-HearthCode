package it.unibo.oop.hearthcode.model.creature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.creature.impl.CreatureDefinitionParser;

/**
 * A simple test for {@link CreatureDefinitionParser}.
 */
final class TestCreatureDefinitionParser {

    private static final String TEST_STRING = "Name card,10,3,5";
    private static final String TEST_NAME = "Name card";
    private static final int TEST_HP = 10;
    private static final int TEST_ATT = 3;
    private static final int TEST_MANA = 5;

    private CreatureDefinitionParser parser;

    @BeforeEach
    void initTest() {
        this.parser = new CreatureDefinitionParser();
    }

    @Test
    void testSingleString() {
        try {
            final var creat = this.parser.parseLine(TEST_STRING);
            assertEquals(TEST_NAME, creat.name());
            assertEquals(TEST_HP, creat.health());
            assertEquals(TEST_ATT, creat.attackValue());
            assertEquals(TEST_MANA, creat.manaCost());
        } catch (final IllegalArgumentException e) {
            fail("Parsing operation failed");
        }
    }
}
