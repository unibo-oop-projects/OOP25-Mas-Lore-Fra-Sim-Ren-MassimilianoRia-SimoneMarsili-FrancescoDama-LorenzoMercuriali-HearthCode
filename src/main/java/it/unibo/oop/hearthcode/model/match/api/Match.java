package it.unibo.oop.hearthcode.model.match.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * The entry point of the model.
 * It represents the main class of the game and applies the basic rules of the game.
 */
public interface Match {

    /**
     * Starts the entire match.
     */
    void startMatch();

    /**
     * @return return whether the game is over or not
     */
    boolean isOver();

    /**
     * @return return the winner of the game if present, empty otherwise
     */
    Optional<PlayerId> getWinner();

}
