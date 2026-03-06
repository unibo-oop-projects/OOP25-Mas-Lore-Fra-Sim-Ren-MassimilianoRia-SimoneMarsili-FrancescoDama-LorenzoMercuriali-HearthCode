package it.unibo.oop.hearthcode.view.api;

import javax.swing.JComponent;

/**
 * It represents a contract for each Scene in the application.
 */
public interface Scene {

    /**
     * Returns the root component of the scene.
     *
     * @return the root component
     */
    JComponent getComponent();

}
