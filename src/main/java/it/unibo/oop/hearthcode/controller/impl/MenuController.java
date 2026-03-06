package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.SceneId;
import it.unibo.oop.hearthcode.view.impl.MenuScene;

/**
 * Controller of the menu scene.
 */
public class MenuController {

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param mainView the application main view
     */
    public MenuController(final MenuScene scene, final MainView mainView) {
        scene.onPlay(() -> mainView.showScene(SceneId.MATCH));
        scene.onSettings(() -> mainView.showScene(SceneId.SETTINGS));
        scene.onQuit(() -> {
            if (mainView.confirmExit()) {
                mainView.close();
            }
        });
    }

}
