package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.SceneId;
import it.unibo.oop.hearthcode.view.api.SettingsView;

/**
 * Controller of the settings scene.
 */
public final class SettingsController {

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param mainView the application main view
     */
    public SettingsController(final SettingsView scene, final MainView mainView) {
        scene.onBack(() -> mainView.showScene(SceneId.MAIN_MENU));
    }
}
