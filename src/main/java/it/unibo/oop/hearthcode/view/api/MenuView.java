package it.unibo.oop.hearthcode.view.api;

/**
 * View contract for the main menu scene.
 */
public interface MenuView extends Scene {

    /**
     * Binds the play action.
     *
     * @param action the action to execute
     */
    void onPlay(Runnable action);

    /**
     * Binds the deck action.
     *
     * @param action the action to execute
     */
    void onDatabase(Runnable action);

    /**
     * Binds the quit action.
     *
     * @param action the action to execute
     */
    void onQuit(Runnable action);

}
