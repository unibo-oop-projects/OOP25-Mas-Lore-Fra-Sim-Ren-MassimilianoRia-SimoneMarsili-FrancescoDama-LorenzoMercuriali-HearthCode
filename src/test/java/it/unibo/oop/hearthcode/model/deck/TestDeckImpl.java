package it.unibo.oop.hearthcode.model.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import it.unibo.oop.hearthcode.model.creature.impl.IdGenerator;
import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.deck.impl.DeckFactory;

/**
 * A simple test for Deck and DeckFactory.
 */
final class TestDeckImpl {

    private static final String TEST_FILE = "creatures.txt";
    private DeckFactory factory;
    private CreatureDatabase db;

    @BeforeEach
    void initTest() {
        this.db = CreatureDatabaseFactory.createFromFile(TEST_FILE);
        this.factory = new DeckFactory(
            this.db, 
            new CreatureImplFactory(new IdGenerator())
        );
    }

    @Test
    void testUniform() {
        final var deck = this.factory.createWeighted(
            this.db.size(), 
            def -> 1
        );

        assertEquals(this.db.size(), deck.getRemaining());

        final List<Card> allDrawn = IntStream.range(0, deck.getRemaining())
            .mapToObj(n -> deck.draw().get())
            .toList();

        assertEquals(this.db.size(), allDrawn.size());

        assertEquals(
            Set.copyOf(this.db.getAll().stream().map(CreatureDefinition::name).toList()), 
            Set.copyOf(allDrawn.stream().map(Card::getName).toList())
        );

        assertEquals(
            allDrawn.stream().count(), 
            allDrawn.stream().map(Card::getId).distinct().count()
        );

    }

    @Test
    void testNonUniform() {
        try {

            final var deck = this.factory.createWeighted(
                this.db.size() * 2, 
                def -> Math.max(1, def.manaCost())
            );

            final List<Card> allDrawn = IntStream.range(0, deck.getRemaining())
            .mapToObj(n -> deck.draw().get())
            .toList();

            assertEquals(this.db.size() * 2, allDrawn.size());

            assertEquals(
                Set.copyOf(this.db.getAll().stream().map(CreatureDefinition::name).toList()), 
                Set.copyOf(allDrawn.stream().map(Card::getName).distinct().toList())
            );
        } catch (final IllegalStateException e) {
            fail(e);
        }
    }
}
