package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.MatchView;
import it.unibo.oop.hearthcode.view.api.SceneId;

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
    public MatchController(final MatchView scene, final MainView mainView) {
        scene.onBack(() -> mainView.showScene(SceneId.MAIN_MENU));
    }
}
