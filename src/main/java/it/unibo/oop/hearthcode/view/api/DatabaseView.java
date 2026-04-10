package it.unibo.oop.hearthcode.view.api;

/**
 * View contract for the database Scene.
 */
public interface DatabaseView extends Scene {

    /**
     * Binds the back action.
     * 
     * @param action the action to execute
     */
    void onBack(Runnable action);

}
