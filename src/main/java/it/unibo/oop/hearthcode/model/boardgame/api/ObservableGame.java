package it.unibo.oop.hearthcode.model.boardgame.api;

/**
 * Represents an observable object in the game.
 * When the state of the object changes, all the {@link GameObserver} are notified.
 */
public interface ObservableGame {

    /**
     * Adds a new GameObserver.
     * 
     * @param obs the new GameObserver
     */
    void addObserver(GameObserver obs);

    /**
     * Remove a specified GameObserver.
     * 
     * @param obs the GameObserver to be removed
     */
    void removeObserver(GameObserver obs);

}
