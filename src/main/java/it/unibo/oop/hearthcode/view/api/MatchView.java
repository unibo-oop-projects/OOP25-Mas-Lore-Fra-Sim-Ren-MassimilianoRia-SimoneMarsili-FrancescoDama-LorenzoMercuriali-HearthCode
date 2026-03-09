package it.unibo.oop.hearthcode.view.api;

/**
 * View contract for the match scene.
 */
public interface MatchView extends Scene {

    /**
     * Binds the back action.
     *
     * @param action the action to execute
     */
    void onBack(Runnable action);
}
