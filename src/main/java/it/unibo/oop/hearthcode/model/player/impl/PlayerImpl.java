package it.unibo.oop.hearthcode.model.player.impl;

import it.unibo.oop.hearthcode.model.creature.api.Card;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.deck.api.Deck;
import it.unibo.oop.hearthcode.model.hand.api.Hand;
import it.unibo.oop.hearthcode.model.hand.impl.HandImpl;
import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * implementation of Player.
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
        final Card removedCard = this.hand.removeCard(cardId);
        this.manaManager.decreaseActualMana(removedCard.getManaCost());
        return removedCard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawCard() {
        this.hand.addCard(this.deck.draw());
    }
}
