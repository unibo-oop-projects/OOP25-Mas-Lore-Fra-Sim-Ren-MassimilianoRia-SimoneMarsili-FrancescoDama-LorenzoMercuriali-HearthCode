package it.unibo.oop.hearthcode.controller.api;

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
     * Create a new match and shows the match scene.
     */
    void startMatch();

    /**
     * Requests the application shutdown.
     */
    void requestExit();

}
