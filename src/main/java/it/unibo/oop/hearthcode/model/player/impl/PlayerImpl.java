package it.unibo.oop.hearthcode.model.player.impl;

import java.util.List;

import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.deck.api.Deck;
import it.unibo.oop.hearthcode.model.hand.api.Hand;
import it.unibo.oop.hearthcode.model.hand.impl.HandImpl;
import it.unibo.oop.hearthcode.model.player.api.DrawCardResult;
import it.unibo.oop.hearthcode.model.player.api.DrawCardResultType;
import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Implementation of {@link Player}.
 */
public class PlayerImpl implements Player {

    private final PlayerId id;
    private final ManaManager manaManager;
    private final Hand hand;
    private final Deck deck;
    private int health;

    PlayerImpl(final Deck deck, final int health, final PlayerId id) {
        this.manaManager = new ManaManager();
        this.hand = new HandImpl();
        this.deck = deck;
        this.health = health;
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerId getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getActualMana() {
        return this.manaManager.actualMana();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTurnManaLimit() {
        return this.manaManager.maxMana();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardState> getHandCardsCopies() {
        return this.hand.getCardsCopies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHandCardsLimit() {
        return this.hand.getMaximumSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDeckCardsCount() {
        return this.deck.getRemaining();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseHealth(final int amount) {
        this.health -= amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementMana() {
        this.manaManager.updateMana();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card playCard(final CardId cardId) {
        final int manaCost = this.hand.getCard(cardId).getManaCost();
        this.manaManager.decreaseActualMana(manaCost);
        return this.hand.removeCard(cardId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawCardResult drawCard() {
        final var drawnCard = this.deck.draw();
        if (drawnCard.isEmpty()) {
            return new DrawCardResult(drawnCard, DrawCardResultType.DECK_EMPTY);
        }
        if (this.hand.getActualSize() < this.hand.getMaximumSize()) {
            this.hand.addCard(drawnCard.get());
            return new DrawCardResult(drawnCard, DrawCardResultType.CARD_ADDED);
        }
        return new DrawCardResult(drawnCard, DrawCardResultType.CARD_BURNED);
    }

}
