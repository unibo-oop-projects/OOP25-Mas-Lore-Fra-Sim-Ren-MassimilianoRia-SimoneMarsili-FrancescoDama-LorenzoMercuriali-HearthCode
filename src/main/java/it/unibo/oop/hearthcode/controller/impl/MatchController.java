package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.view.api.MatchView;

/**
 * Controller of the match scene.
 */
public final class MatchController {

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param coordinator the application scene coordinator
     * @param audioService the audio service
     */
    public MatchController(
        final MatchView scene,
        final SceneCoordinator coordinator,
        final AudioService audioService
    ) {
        scene.onBack(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            coordinator.showMainMenu();
        });
    }
}
