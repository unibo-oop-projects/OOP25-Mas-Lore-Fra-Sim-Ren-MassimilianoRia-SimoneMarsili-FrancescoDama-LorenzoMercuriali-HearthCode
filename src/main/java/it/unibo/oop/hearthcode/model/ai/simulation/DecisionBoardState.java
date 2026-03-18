package it.unibo.oop.hearthcode.model.ai.simulation;

import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;

/**
 * Snapshot representation of the {@link BoardGame} state at a specific moment used in the AI decision simulation layer.
 * Implementations should be immutable.
 */
public interface DecisionBoardState {

    /**
     * @return the state of the player whose turn it is
     */
    ActingPlayerState getActingPlayer();

    /**
     * @return the state of the opponent player
     */
    OpponentPlayerState getOpponentPlayer();

    /**
     * @return the state of the army of the acting player
     */
    ArmyState getActingArmy();

    /**
     * @return the state of the army of the opponent player
     */
    ArmyState getOpponentArmy();
    
    /**
     * @return return whether the game is over or not
     */
    boolean isOver();

    /**
     * @return the current turn number
     */
    int getTurnNumber();

}
