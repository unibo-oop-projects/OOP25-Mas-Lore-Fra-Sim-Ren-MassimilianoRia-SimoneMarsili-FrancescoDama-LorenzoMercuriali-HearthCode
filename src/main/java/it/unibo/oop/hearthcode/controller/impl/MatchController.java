package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.model.ai.executor.api.AiActionExecutor;
import it.unibo.oop.hearthcode.model.ai.service.api.AiTurnService;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.boardgame.api.ObservableGame;
import it.unibo.oop.hearthcode.view.api.MatchView;

/**
 * Controller of the match scene.
 */
public final class MatchController {

    private static final String MESSAGE = "Incorrect number of cards selected!";

    private final BoardGame boardGame;

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param boardGame the boardGame of the match
     * @param coordinator the application scene coordinator
     * @param audioService the audio service
     * @param aiTurnService the turn service of the AI
     * @param aiActionExecutor the executor of the AI actions in the real match
     */
    public MatchController(
        final MatchView scene,
        final BoardGame boardGame,
        final SceneCoordinator coordinator,
        final AudioService audioService,
        final AiTurnService aiTurnService,
        final AiActionExecutor aiActionExecutor
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
                scene.showErrorPanel(MESSAGE);
            }
            final var winner = this.boardGame.getWinner();
            if (winner.isPresent()) {
                coordinator.showEndMatch(winner.get());
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
                scene.showErrorPanel(MESSAGE);
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
                scene.showErrorPanel(MESSAGE);
            }
        });

        scene.onEndTurn(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            this.boardGame.switchTurn();
            aiTurnService.decideTurn(this.boardGame).stream().forEach(
                action -> aiActionExecutor.execute(this.boardGame, action)
            );
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
