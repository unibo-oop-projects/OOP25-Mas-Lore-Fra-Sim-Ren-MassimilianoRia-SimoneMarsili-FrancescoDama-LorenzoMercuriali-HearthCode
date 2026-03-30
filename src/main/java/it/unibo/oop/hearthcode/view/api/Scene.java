package it.unibo.oop.hearthcode.view.api;

import javax.swing.JComponent;

/**
 * Contract for an application scene.
 */
@FunctionalInterface
public interface Scene {

    /**
     * Returns the root component of the scene.
     *
     * @return the root component
     */
    JComponent getComponent();

}
