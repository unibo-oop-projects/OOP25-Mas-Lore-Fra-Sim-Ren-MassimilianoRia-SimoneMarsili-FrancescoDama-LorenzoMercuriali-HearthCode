package it.unibo.oop.hearthcode.model.boardgame.impl;

import java.util.Map;

import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Immutable match setup used to initialize a board game instance.
 */
public final class GameSetup {

    private final Map<PlayerId, Player> players;
    private final Map<Player, Army> armies;
    private final PlayerId startingPlayerId;

    /**
     * Creates a new immutable match setup.
     *
     * @param players the players participating in the match
     * @param armies the armies associated with each player
     * @param startingPlayerId the player who starts the match
     */
    public GameSetup(
        final Map<PlayerId, Player> players,
        final Map<Player, Army> armies,
        final PlayerId startingPlayerId
    ) {
        this.players = Map.copyOf(players);
        this.armies = Map.copyOf(armies);
        this.startingPlayerId = startingPlayerId;
    }

    /**
     * Returns the configured players.
     * 
     * @return immutable copy of the configured players
     */
    public Map<PlayerId, Player> players() {
        return Map.copyOf(this.players);
    }

    /**
     * Returns the armies associated with the configured players.
     * 
     * @return immutable copy of the configured armies
     */
    public Map<Player, Army> armies() {
        return Map.copyOf(this.armies);
    }

    /**
     * Returns the identifier of the player who starts the match.
     * 
     * @return the player that starts the game
     */
    public PlayerId startingPlayerId() {
        return this.startingPlayerId;
    }
}
