package it.unibo.oop.hearthcode.view.api;

/**
 * View contract for the match scene.
 */
public interface MatchView extends Scene {

    /**
     * Binds the attack action.
     *
     * @param action the action to execute
     */
    void onAttack(Runnable action);

    /**
     * Binds the end turn action.
     *
     * @param action the action to execute
     */
    void onEndTurn(Runnable action);

}
