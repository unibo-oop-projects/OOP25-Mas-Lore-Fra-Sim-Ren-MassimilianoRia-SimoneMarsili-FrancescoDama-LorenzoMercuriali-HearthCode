package it.unibo.oop.hearthcode.model.player.impl;

import it.unibo.oop.hearthcode.model.deck.api.Deck;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * a simple factory for {@link PlayerImpl}.
 */
public final class PlayerFactory {

    private PlayerFactory() { }

    /**
     * Static method to create a player with a customized deck and health.
     * 
     * @param deck the deck to be assigned
     * @param health the health to be assigned
     * @param identifier the PlayerId to be assigned
     * @return a new player with those parameters
     */
    public static PlayerImpl createPlayer(final Deck deck, final int health, final PlayerId identifier) {
        return new PlayerImpl(deck, health, identifier);
    }
}
