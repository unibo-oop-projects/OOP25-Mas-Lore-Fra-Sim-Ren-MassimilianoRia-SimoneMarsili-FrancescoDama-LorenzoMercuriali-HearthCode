package it.unibo.oop.hearthcode.view.api;

/**
 * Main application window.
 */
public interface MainView {

    /**
     * Makes the UI visible.
     */
    void show();

    /**
     * Closes the application window.
     */
    void close();

    /**
     * Registers a scene inside the main window.
     *
     * @param id the scene identifier
     * @param scene the scene to register
     */
    void addScene(SceneId id, Scene scene);

    /**
     * Shows the selected scene.
     *
     * @param id the scene identifier
     */
    void showScene(SceneId id);

    /**
     * @return true if the user wants to exit, false otherwise.
     */
    boolean confirmExit();

}
