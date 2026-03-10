package it.unibo.oop.hearthcode.model.deck.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.deck.api.Deck;

/**
 * An implementation of {@link Deck} interface.
 */
public class DeckImpl implements Deck {

    private final List<Card> cards;

    DeckImpl(final List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card draw() {
        if (getRemaining() == 0) {
            throw new IllegalStateException("It's not possible drawing from an empty deck.");
        }
        return this.cards.remove(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getRemaining() {
        return this.cards.size();
    }

}
