package it.unibo.oop.hearthcode.model.ai.api;

import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Representation of a player state used by the AI.
 */
public interface PlayerState {

    /**
     * @return the player identifier
     */
    PlayerId getPlayerId();

    /**
     * @return the player health
     */
    int getPlayerHealth();

    /**
     * @return the current mana value
     */
    int getPlayerActualMana();

    /**
     * @return an optional containing the player's hand when available
     */
    Optional<List<CardState>> getPlayerHand();

    /**
     * @return the list of cards currently deployed in the army
     */
    List<CardState> getPlayerArmy();

}
