package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.view.api.EndMatchView;

/**
 * Controller of the end match scene.
 */
public final class EndMatchController {

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param coordinator the application scene coordinator
     * @param audioService the audio service
     */
    public EndMatchController(
        final EndMatchView scene,
        final SceneCoordinator coordinator,
        final AudioService audioService
    ) {
        scene.onMenu(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            coordinator.showMainMenu();
        });
    }

}
