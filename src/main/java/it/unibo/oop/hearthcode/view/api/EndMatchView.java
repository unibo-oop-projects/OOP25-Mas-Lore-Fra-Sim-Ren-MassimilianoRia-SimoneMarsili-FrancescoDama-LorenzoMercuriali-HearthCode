package it.unibo.oop.hearthcode.view.api;

/**
 * View contract for the end match scene.
 */
public interface EndMatchView extends Scene {

    /**
     * Binds the back action.
     * 
     * @param action the action to execute
     */
    void onMenu(Runnable action);

}
