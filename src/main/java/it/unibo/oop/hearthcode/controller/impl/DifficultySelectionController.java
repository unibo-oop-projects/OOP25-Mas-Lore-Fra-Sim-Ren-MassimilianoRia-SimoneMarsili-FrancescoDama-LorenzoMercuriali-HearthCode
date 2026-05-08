package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.model.boardgame.api.Difficulty;
import it.unibo.oop.hearthcode.view.api.DifficultySelectionView;

/**
 * Controller of the difficulty selection scene.
 */
public final class DifficultySelectionController {

    /**
     * Builds the controller and binds the scene actions.
     * 
     * @param scene the controlled scene
     * @param coordinator the application scene coordinator.
     * @param audioService the audio service
     */
    public DifficultySelectionController(
        final DifficultySelectionView scene,
        final SceneCoordinator coordinator,
        final AudioService audioService
    ) {
        scene.onNormal(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            coordinator.startMatch(Difficulty.NORMAL);
        });
        scene.onHard(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            coordinator.startMatch(Difficulty.HARD);
        });
        scene.onBack(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            coordinator.showMainMenu();
        });
    }

}
