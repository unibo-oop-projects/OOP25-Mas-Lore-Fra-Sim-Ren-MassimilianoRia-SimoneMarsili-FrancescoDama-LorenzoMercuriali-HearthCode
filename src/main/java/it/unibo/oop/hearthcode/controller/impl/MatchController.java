package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.SceneId;
import it.unibo.oop.hearthcode.view.impl.MatchScene;

/**
 * Controller of the match scene.
 */
public final class MatchController {

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param mainView the application main view
     */
    public MatchController(final MatchScene scene, final MainView mainView) {
        scene.onBack(() -> mainView.showScene(SceneId.MAIN_MENU));
    }
}