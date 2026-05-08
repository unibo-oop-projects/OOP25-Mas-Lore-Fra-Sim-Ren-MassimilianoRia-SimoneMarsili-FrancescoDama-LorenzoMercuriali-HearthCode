package it.unibo.oop.hearthcode.model.army;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.army.impl.ArmyImpl;
import it.unibo.oop.hearthcode.model.creature.impl.IdGenerator;
import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Creature;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.deck.api.Deck;
import it.unibo.oop.hearthcode.model.deck.impl.DeckFactory;

final class TestArmy {
    private static final String TEST_FILE = "creatures.txt";
    private Army army;
    private Deck deck;

    @BeforeEach
    void initTest() {
        final CreatureDatabase db = CreatureDatabaseFactory.createFromFile(TEST_FILE);
        final DeckFactory factory = new DeckFactory(
            db, 
            new CreatureImplFactory(new IdGenerator())
        );
        this.deck = factory.createWeighted(db.size(), def -> 1);
        this.army = new ArmyImpl();
    }

    @Test
    void testAwaken() {
        final Card card = this.deck.draw().get();
        final Creature creature = (Creature) card;
        final CardId id = creature.getId();
        this.army.placeCard(creature);
        assertFalse(this.army.canAttack(id));
        this.army.awakeCreatures();
        assertTrue(this.army.canAttack(id));
        this.army.disableAttack(id);
        assertFalse(this.army.canAttack(id));
    }

    @Test
    void testModifications() {
        final Card card = this.deck.draw().get();
        final Creature creature = (Creature) card;
        final int health = creature.getHealth();
        final CardId id = creature.getId();
        this.army.placeCard(creature);
        final Optional<Creature> armyCard = this.army.getPlacedCard(id);
        final Creature modifiedCard = armyCard.get();
        modifiedCard.decreaseHealth(1);
        final Creature testCard = this.army.getPlacedCard(id).get();
        assertEquals(testCard.getHealth(), health - 1);

    }

    @Test
    void testPlacingAndDeath() {
        final Card card = this.deck.draw().get();
        final Creature creature = (Creature) card;
        final CardId id = creature.getId(); 
        this.army.placeCard(creature);
        creature.decreaseHealth(10);
        if (creature.getHealth() == 0) {
            this.army.deleteDeathCreature(id);
        }
        assertTrue(this.army.getPlacedCard(id).isEmpty());
    }
}
