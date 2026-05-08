package it.unibo.oop.hearthcode.controller.api;

import it.unibo.oop.hearthcode.model.boardgame.api.Difficulty;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Coordinates scene transitions at application level.
 */
public interface SceneCoordinator {

    /**
     * Shows the main menu scene.
     */
    void showMainMenu();

    /**
     * Shows the Database scene.
     */
    void showDatabase();

    /**
     * Shows the end match scene.
     * 
     * @param playerId the identifier of the winner
     */
    void showEndMatch(PlayerId playerId);

    /**
     * Starts a new match with the specified difficulty and shows the match scene.
     * 
     * @param difficulty the selected match difficulty
     */
    void startMatch(Difficulty difficulty);

    /**
     * Shows the difficulty selection scene.
     */
    void showDifficultySelection();

    /**
     * Requests the application shutdown.
     */
    void requestExit();

}
