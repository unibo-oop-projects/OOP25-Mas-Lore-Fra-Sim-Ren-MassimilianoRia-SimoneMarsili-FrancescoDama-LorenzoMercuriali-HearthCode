package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.boardgame.api.ObservableGame;
import it.unibo.oop.hearthcode.view.api.MatchView;

/**
 * Controller of the match scene.
 */
public final class MatchController {

    private final BoardGame boardGame;

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param boardGame the boardGame of the match
     * @param coordinator the application scene coordinator
     * @param audioService the audio service
     */
    public MatchController(
        final MatchView scene,
        final BoardGame boardGame,
        final SceneCoordinator coordinator,
        final AudioService audioService
    ) {

        if (boardGame instanceof ObservableGame observable && scene instanceof GameObserver observer) {
            observable.addObserver(observer);
        }

        this.boardGame = boardGame;
        this.boardGame.startGame();

        scene.onAttackHero(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            //this.boardGame.attackCard(null, null);
        });

        scene.onAttackCreature(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            this.boardGame.switchTurn();
        });

        scene.onPlaceCard(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            //this.boardGame.attackCard(null, null);
        });

        scene.onEndTurn(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            //this.boardGame.attackCard(null, null);
        });

        scene.onExitGame(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
        });
    }
}
