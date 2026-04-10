package it.unibo.oop.hearthcode.controller.api;

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
     * Shows the settings scene.
     */
    void showSettings();

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
     * Create a new match and shows the match scene.
     */
    void startMatch();

    /**
     * Requests the application shutdown.
     */
    void requestExit();

}
