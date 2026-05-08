package it.unibo.oop.hearthcode.model.creature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.creature.impl.IdGenerator;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;

final class TestCreatureImpl {

    private static final String TEST_NAME = "Name";
    private static final int TEST_HEALTH = 5;
    private static final int TEST_ATTACK = 1;
    private static final int TEST_MANA = 1;
    private CreatureImplFactory factory;

    @BeforeEach
    void initTest() {
        this.factory = new CreatureImplFactory(new IdGenerator());
    }

    @Test
    void testCreaturefromFactory() {
        final var def = new CreatureDefinition(TEST_NAME, TEST_HEALTH, TEST_ATTACK, TEST_MANA);
        final var c1 = this.factory.createFromDefinition(def);
        assertEquals(1, c1.getId().id());
        assertEquals(TEST_HEALTH, c1.getHealth());
        c1.decreaseHealth(2);
        assertEquals(3, c1.getHealth());
        c1.decreaseHealth(TEST_HEALTH);
        assertEquals(0, c1.getHealth());
        final var c2 = this.factory.createFromDefinition(def);
        assertEquals(2, c2.getId().id());
    }

}
