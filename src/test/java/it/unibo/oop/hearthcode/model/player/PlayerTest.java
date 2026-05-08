package it.unibo.oop.hearthcode.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.creature.impl.IdGenerator;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.deck.api.Deck;
import it.unibo.oop.hearthcode.model.deck.impl.DeckFactory;
import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.impl.PlayerFactory;
import it.unibo.oop.hearthcode.model.player.impl.PlayerImpl;

/**
 * A simple test for {@link PlayerImpl}.
 */
final class PlayerTest {
    private static final int EXPECTED_HEALTH = 6;
    private static final String TEST_FILE = "creatures.txt";
    private Player player;

    @BeforeEach
    void initTest() {
        final CreatureDatabase db = CreatureDatabaseFactory.createFromFile(TEST_FILE);
        final DeckFactory factory = new DeckFactory(
            db, 
            new CreatureImplFactory(new IdGenerator())
        );
        final Deck deck = factory.createWeighted(db.size(), def -> 1);
        final PlayerId id = PlayerId.HUMAN;
        this.player = PlayerFactory.createPlayer(deck, 10, id);
    }

    @Test
    void testMana() {
        assertEquals(this.player.getActualMana(), this.player.getTurnManaLimit());
        this.player.incrementMana();
        assertEquals(this.player.getActualMana(), this.player.getTurnManaLimit());
    }

    @Test
    void testHealth() {
        assertEquals(this.player.getHealth(), 10);
        this.player.decreaseHealth(4);
        assertEquals(this.player.getHealth(), EXPECTED_HEALTH);
    }
}
