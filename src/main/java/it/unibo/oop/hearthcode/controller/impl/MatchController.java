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
            if (scene.getSelectedCards().size() == 1) {
                try {
                    this.boardGame.attackHero(scene.getSelectedCards().get(0));
                } catch (final IllegalArgumentException | IllegalStateException e) {
                    scene.showErrorPanel(e.getMessage());
                }
            } else {
                scene.showErrorPanel("Incorrect number of cards selected!");
            }
        });

        scene.onAttackCreature(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            if (scene.getSelectedCards().size() == 2) {
                try {
                    this.boardGame.attackCard(scene.getSelectedCards().get(0), scene.getSelectedCards().get(1));
                } catch (final IllegalArgumentException | IllegalStateException e) {
                    scene.showErrorPanel(e.getMessage());
                }
            } else {
                scene.showErrorPanel("Incorrect number of cards selected!");
            }
        });

        scene.onPlaceCard(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            if (scene.getSelectedCards().size() == 1) {
                try {
                    this.boardGame.place(scene.getSelectedCards().get(0));
                } catch (final IllegalArgumentException | IllegalStateException e) {
                    scene.showErrorPanel(e.getCause().getMessage());
                }
            } else {
                scene.showErrorPanel("Incorrect number of cards selected!");
            }
        });

        scene.onEndTurn(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            this.boardGame.switchTurn();
        });

        scene.onExitGame(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            if (scene.confirmExitGame()) {
                coordinator.showMainMenu();
            }
        });
    }
}
