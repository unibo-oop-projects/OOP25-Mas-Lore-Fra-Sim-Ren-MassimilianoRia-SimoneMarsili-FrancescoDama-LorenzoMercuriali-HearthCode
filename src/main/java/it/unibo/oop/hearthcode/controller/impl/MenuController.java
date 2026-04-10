package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.view.api.MenuView;

/**
 * Controller of the menu scene.
 */
public final class MenuController {

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param coordinator the application scene coordinator
     * @param audioService the audio service
     */
    public MenuController(
        final MenuView scene,
        final SceneCoordinator coordinator,
        final AudioService audioService
    ) {
        scene.onPlay(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            coordinator.startMatch();
        });
        scene.onDatabase(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            coordinator.showDatabase();
        });
        scene.onQuit(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            coordinator.requestExit();
        });
    }

}

