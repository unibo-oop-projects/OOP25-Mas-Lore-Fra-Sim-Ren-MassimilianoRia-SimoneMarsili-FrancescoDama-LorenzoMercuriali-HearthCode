package it.unibo.oop.hearthcode.model.player.impl;

import it.unibo.oop.hearthcode.model.deck.api.Deck;

/**
 * a simple factory for {@link PlayerImpl}.
 */
public final class PlayerFactory {

    private PlayerFactory() { }

    /**
     * static method to create a player with a customized eck and health.
     * 
     * @param deck the deck to be assigned
     * @param health the health to be assigned
     * @return a new player with those parameters
     */
    public static PlayerImpl createPlayer(final Deck deck, final int health) {
        return new PlayerImpl(deck, health);
    }
}
