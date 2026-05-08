package it.unibo.oop.hearthcode.view.api;

import java.util.List;

import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * View contract for the match scene.
 */
public interface MatchView extends Scene, GameObserver {

    /**
     * @return the list of clicked cards.
     */
    List<CardId> getSelectedCards();

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
     * @param action the action to execute
     */
    void onAttackCreature(Runnable action);

    /**
     * Binds the action to quit the game.
     * 
     * @param action the action to execute
     */
    void onExitGame(Runnable action);

    /**
     * A warning panel to confirm the exit.
     * 
     * @return whether the player wants to exit or not
     */
    boolean confirmExitGame();

    /**
     * Creates a dialog showing an error message.
     * 
     * @param s the message to be shown
     */
    void showErrorPanel(String s);

}
