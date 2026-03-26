package it.unibo.oop.hearthcode.model.hand.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.hand.api.Hand;

/**
 * Implementation of Hand's interface.
 */
public class HandImpl implements Hand {

    private static final int MAXIMUM_SIZE = 7;

    private final List<Card> hand;

    /**
     * simple constructor.
     */
    public HandImpl() {
        this.hand = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumSize() {
        return MAXIMUM_SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getActualSize() {
        return this.hand.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCard(final Card card) {
        this.hand.add(card);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card removeCard(final CardId cardId) {
        final Optional<Card> card = this.hand.stream()
            .filter(c -> c.getId().equals(cardId))
            .findFirst();
        if (card.isEmpty()) {
            throw new IllegalArgumentException("This card is not contained in your hand!");
        } else {
            this.hand.remove(card.get());
            return card.get();
        }
    }
}
