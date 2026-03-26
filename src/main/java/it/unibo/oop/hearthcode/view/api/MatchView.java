package it.unibo.oop.hearthcode.view.api;

/**
 * View contract for the match scene.
 */
public interface MatchView extends Scene {

    /**
     * Binds the attack on hero action.
     *
     * @param action the action to execute
     */
    void onAttackHero(Runnable action);

    /**
     * Binds the end turn action.
     *
     * @param action the action to execute
     */
    void onEndTurn(Runnable action);

    /**
     * Binds the place card action.
     * 
     * @param action the action to execute
     */
    void onPlaceCard(Runnable action);

    /**
     * Binds the attack on a creature action.
     * 
     * @param action
     */
    void onAttackCard(Runnable action);
}
