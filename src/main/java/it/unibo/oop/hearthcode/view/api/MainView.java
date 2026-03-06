package it.unibo.oop.hearthcode.view.api;

/**
 * It represents the main frame of the application.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface MainView {

    /**
     * Makes the UI visible.
     */
    void show();

    /**
     * Closes the application window.
     */
    void close();

}
