package it.unibo.oop.hearthcode.model.deck.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.deck.api.Deck;

/**
 * An implementation of the {@link Deck} interface.
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
    public Optional<Card> draw() {
        if (getRemaining() == 0) {
            return Optional.empty();
        }
        return Optional.of(this.cards.remove(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getRemaining() {
        return this.cards.size();
    }

}
