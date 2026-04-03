package it.unibo.oop.hearthcode.view.api;

/**
 * View contract for the settings scene.
 */
public interface SettingsView extends Scene {

    /**
     * Binds the back action.
     *
     * @param action the action to execute
     */
    void onBack(Runnable action);

    /**
     * Binds the Database showing action.
     * 
     * @param action the action to execute
     */
    void onDatabase(Runnable action);
}
