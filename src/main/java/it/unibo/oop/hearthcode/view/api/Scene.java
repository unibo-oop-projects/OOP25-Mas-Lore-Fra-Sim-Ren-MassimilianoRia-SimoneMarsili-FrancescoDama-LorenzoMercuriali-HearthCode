package it.unibo.oop.hearthcode.view.api;

import javax.swing.JComponent;

/**
 * Contract for an application scene.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface Scene {

    /**
     * Returns the root component of the scene.
     *
     * @return the root component
     */
    JComponent getComponent();

}
