package it.unibo.oop.hearthcode.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.deck.api.Deck;
import it.unibo.oop.hearthcode.model.deck.impl.DeckFactory;
import it.unibo.oop.hearthcode.model.game.impl.IdGenerator;
import it.unibo.oop.hearthcode.model.hand.api.Hand;
import it.unibo.oop.hearthcode.model.hand.impl.HandImpl;

/**
 * a simple test for {@link HandImpl}.
 */
final class TestHand {

    private static final String TEST_FILE = "creatures.txt";
    private Hand hand;
    private Deck deck;

    @BeforeEach
    void initTest() {
        this.hand = new HandImpl();
        final CreatureDatabase db = CreatureDatabaseFactory.createFromFile(TEST_FILE);
        final DeckFactory factory = new DeckFactory(
            db, 
            new CreatureImplFactory(new IdGenerator())
        );
        this.deck = factory.createWeighted(db.size(), def -> 1);
    }

    @Test
    void testAddCard() {
        this.hand.addCard(this.deck.draw());
        assertEquals(1, this.hand.getSize());
        this.hand.addCard(this.deck.draw());
        this.hand.addCard(this.deck.draw());
        assertEquals(3, this.hand.getSize());
    }

    @Test
    void testRemoveCard() {
        final Card card = this.deck.draw();
        final CardId id = card.getId();
        this.hand.addCard(card);
        final Card removedCard = this.hand.removeCard(id);
        assertEquals(0, this.hand.getSize());
        assertEquals(removedCard.getId(), id);
    }

    @Test
    void testException() {
        this.hand.addCard(this.deck.draw());
        assertThrows(IllegalArgumentException.class, () -> {
            this.hand.removeCard(this.deck.draw().getId());
        });
    }
}
