package it.unibo.oop.hearthcode.model.deck.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.model.deck.api.Deck;
import it.unibo.oop.hearthcode.model.deck.api.WeightStrategy;

/**
 * A simple Deck factory.
 */
public class DeckFactory {

    private final CreatureDatabase database;
    private final CreatureImplFactory factory;
    private final Random random;

    /**
     * Constructs a new DeckFactory.
     * 
     * @param database the creature database to choose cards from 
     * @param factory the factory used to create creatures
     */
    public DeckFactory(final CreatureDatabase database, final CreatureImplFactory factory) {
        this.database = database;
        this.factory = factory;
        this.random = new Random();
    }

    /**
     * Creates a deck using a {@link WeightStrategy}.
     * Basically, a strong card is less likely to be chosen twice than a weaker one.
     * 
     * @param size the size of the deck to be created
     * @param strategy the weight strategy used to create the deck
     * @return the new shuffled deck
     */
    public Deck createWeighted(final int size, final WeightStrategy strategy) {
        final var allDef = this.database.getAll();
        if (allDef.isEmpty()) {
            throw new IllegalStateException("Cannot create a deck from an empty database.");
        }

        final List<Card> cards = new ArrayList<>();

        if (size >= this.database.size()) {
            allDef.forEach(d -> cards.add(this.factory.createFromDefinition(d)));
        }

        IntStream.range(0, size - cards.size())
            .forEach(n -> cards.add(
                this.factory.createFromDefinition(
                    pickRandomWeighted(allDef, strategy))
            )
        );

        Collections.shuffle(cards);
        return new DeckImpl(cards);
    }

    /*
    Helper method used to draw cards with a non-uniform probability.
    It uses the WeightStrategy to sum all creature weights and choose a random value in that range;
    it then returns the corresponding creature definition.
    */
    private CreatureDefinition pickRandomWeighted(final List<CreatureDefinition> allDef, final WeightStrategy strategy) {
        final int weightsSum = allDef.stream().mapToInt(strategy::getWeight).sum();

        final int randomWeight = this.random.nextInt(weightsSum);

        int weightIndex = 0;
        for (final var def : allDef) {
            weightIndex += strategy.getWeight(def);
            if (randomWeight < weightIndex) {
                return def;
            }
        }

        throw new IllegalStateException("An error occurred during Weight calculation.");

    }
}
